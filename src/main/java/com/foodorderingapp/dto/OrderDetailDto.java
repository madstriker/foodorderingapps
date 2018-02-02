package com.foodorderingapp.dto;

import java.util.Date;

public class OrderDetailDto {


    private int orderId;
    private String firstName;
    private String middleName;
    private String lastName;
    private String foodName;
    private double foodPrice;
    private String restaurantName;
    private int quantity;
    private Date orderedDate;


    public OrderDetailDto() {

    }

    public OrderDetailDto(int orderId, String firstName, String middleName, String lastName, String foodName, double foodPrice, String restaurantName, int quantity, Date orderedDate) {
        this.orderId = orderId;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.foodName = foodName;
        this.foodPrice = foodPrice;
        this.restaurantName = restaurantName;
        this.quantity = quantity;
        this.orderedDate = orderedDate;
    }

    public Date getOrderedDate() {
        return orderedDate;
    }

    public void setOrderedDate(Date orderedDate) {
        this.orderedDate = orderedDate;
    }

    public double getFoodPrice() {
        return foodPrice;
    }

    public void setFoodPrice(double foodPrice) {
        this.foodPrice = foodPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }


    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }


}
