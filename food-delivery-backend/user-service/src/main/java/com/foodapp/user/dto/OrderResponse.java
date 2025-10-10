package com.foodapp.user.dto;

public class OrderResponse {
    private Long id;
    private String status;
    private Double totalAmount;
    private String restaurantName;
    private String orderDate;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getRestaurantName() {
        return restaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        this.restaurantName = restaurantName;
    }

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String orderDate) {
        this.orderDate = orderDate;
    }
import lombok.Data;

@Data
public class OrderResponse {
    private String orderId;
    private String status;
    private String restaurantName;
    private Double totalAmount;
    private String orderDate;
}