package com.foodapp.user.dto;

import lombok.Data;

@Data
public class OrderResponse {
    private String orderId;
    private String status;
    private String restaurantName;
    private Double totalAmount;
    private String orderDate;
}