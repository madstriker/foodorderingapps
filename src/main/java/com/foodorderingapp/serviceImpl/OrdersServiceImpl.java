package com.foodorderingapp.serviceImpl;

import com.foodorderingapp.dao.OrderDAO;
import com.foodorderingapp.dao.RestaurantDAO;
import com.foodorderingapp.dto.*;
import com.foodorderingapp.exception.DataNotFoundException;
import com.foodorderingapp.model.Food;
import com.foodorderingapp.model.OrderDetail;
import com.foodorderingapp.model.Orders;
import com.foodorderingapp.model.User;
import com.foodorderingapp.service.FoodService;
import com.foodorderingapp.service.OrderDetailService;
import com.foodorderingapp.service.OrdersService;
import com.foodorderingapp.service.UserService;
import com.foodorderingapp.utils.FoodResUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrdersServiceImpl implements OrdersService {

    private final UserService userService;
    private final FoodService foodService;
    private final OrderDAO orderDAO;
    private final OrderDetailService orderDetailService;

    @Autowired
    public OrdersServiceImpl(UserService userService,FoodService foodService,OrderDAO orderDAO,OrderDetailService orderDetailService){
        this.userService=userService;
        this.foodService=foodService;
        this.orderDAO=orderDAO;
        this.orderDetailService=orderDetailService;
    }

    double balance;
    public BillDto add(OrderDto orderDto) {

        BillDto bal=new BillDto();
        List<Food> foodList=new ArrayList<>();
        User user=userService.getUser(orderDto.getUserId());
        if(user == null){
            throw new DataNotFoundException("user not found.");
        }
        Orders orders = new Orders();
        orders.setUser(user);
        orders.setConfirm(false);
        orderDAO.add(orders);

        for(FoodQuantity foodQuantity : orderDto.getFoodList()) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrders(orders);
            orderDetail.setQuantity(foodQuantity.getQuantity());
            orderDetail.setFoodName(foodQuantity.getFoodName());
            orderDetail.setRestaurantName(foodQuantity.getRestaurantName());
            orderDetail.setFoodPrice(foodQuantity.getFoodPrice());
            Food food=foodService.getFoodByResName(foodQuantity.getRestaurantName(),foodQuantity.getFoodName());
            if(foodQuantity.getFoodPrice()!=food.getPrice()){
                throw new DataNotFoundException("price not found");
            }

            if(foodQuantity.getQuantity()<=0){
                throw new DataNotFoundException(" quantity should be greater than 0");
            }
            foodList.add(food);
            double amount=foodQuantity.getQuantity()*foodQuantity.getFoodPrice();
            balance=user.getBalance()-amount;
            user.setBalance(balance);
            userService.update(user);
            bal.setBalance(balance);
            bal.setFoodList(foodList);
            orderDetailService.add(orderDetail);
        }
        return  bal;
        }

    public List<OrderListDto> getOrder() {

        try{
            List<OrderListMapperDto> orderListMapperDtoList=orderDAO.getOrders();
            List<OrderListDto> orderListDtoList=new ArrayList<OrderListDto>();

            for(OrderListMapperDto orderListMapperDto:orderListMapperDtoList){
                OrderListDto orderListDto=new OrderListDto();
                List<FoodRes> foodResList=new ArrayList();
                orderListDto.setOrderId(orderListMapperDto.getOrderId());
                orderListDto.setUserId(orderListMapperDto.getUserId());
                orderListDto.setOrderedDate(orderListMapperDto.getOrderedDate());
                List<OrderDetail> orderDetailList=orderDetailService.getOrderDetailByOrderId(orderListMapperDto.getOrderId());

                for(OrderDetail orderDetail:orderDetailList){
                    foodResList.add(FoodResUtil.addFoodRes(orderDetail));
                    orderListDto.setFoodResList(foodResList);
                }
                orderListDtoList.add(orderListDto);
            }
            return  orderListDtoList;
        }catch(RuntimeException e){
            throw new DataNotFoundException("Cannot find order list");
        }
    }
    public List<UserListDto> getByUserId(int userId) {

        try {
            List<UserListMapperDto> userListMapperDtos = userService.getByUserId(userId);
            List<UserListDto> userListDtoList = new ArrayList<>();

            for (UserListMapperDto userListMapperDto : userListMapperDtos) {
                UserListDto userListDto = new UserListDto();
                List<FoodRes> foodResList = new ArrayList<>();
                userListDto.setUserId(userListMapperDto.getUserId());
                userListDto.setOrderId(userListMapperDto.getOrderId());
                userListDto.setFirstName(userListMapperDto.getFirstName());
                userListDto.setMiddleName(userListMapperDto.getMiddleName());
                userListDto.setLastName(userListMapperDto.getLastName());
                userListDto.setOrderedDate(userListMapperDto.getOrderedDate());
                List<OrderDetail> orderDetailList = orderDetailService.getOrderDetailByOrderId(userListMapperDto.getOrderId());

                for (OrderDetail orderDetail : orderDetailList) {
                    foodResList.add(FoodResUtil.addFoodRes(orderDetail));
                    userListDto.setFoodResList(foodResList);
                }
                userListDtoList.add(userListDto);
            }
            return userListDtoList;
        }
        catch (Exception e) {
            throw new DataNotFoundException("Cannot find order list");
        }
    }

    public Orders update(int orderId) {
        Orders orders1=orderDAO.getOrder(orderId);
        orders1.setConfirm(true);
        orderDAO.update(orders1);
        return orders1;
    }
}

