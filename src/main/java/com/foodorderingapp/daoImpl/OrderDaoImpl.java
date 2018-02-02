package com.foodorderingapp.daoImpl;

import com.foodorderingapp.dao.OrderDAO;
import com.foodorderingapp.dao.OrderDetailDAO;
import com.foodorderingapp.dto.OrderListMapperDto;
import com.foodorderingapp.exception.BadRequestException;
import com.foodorderingapp.model.Orders;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDAO{

    private final SessionFactory sessionFactory;
    private final OrderDetailDAO orderDetailDAO;

    @Autowired
    public OrderDaoImpl(SessionFactory sessionFactory ,OrderDetailDAO orderDetailDAO){
        this.sessionFactory=sessionFactory;
        this.orderDetailDAO=orderDetailDAO;
    }

    public Orders add(Orders orders) {
        try {
            sessionFactory.getCurrentSession().save(orders);
            return  orders;
        } catch (Exception e) {
            throw new BadRequestException("cannnot add order");
        }
    }

    public List<OrderListMapperDto> getOrders() {
        Query qry=sessionFactory
                .getCurrentSession()
                .createNativeQuery("select tbl_orders.order_id ,tbl_orders.ordered_date, tbl_orders.user_id " +
                "from tbl_orders where tbl_orders.confirm=false","OrderMapping");
        return qry.getResultList();
    }
    public void update(Orders orders) {
        sessionFactory.getCurrentSession().update(orders);
    }

    public Orders getOrder(int orderId) {
        return  sessionFactory.getCurrentSession().get(Orders.class,orderId);
    }
}
