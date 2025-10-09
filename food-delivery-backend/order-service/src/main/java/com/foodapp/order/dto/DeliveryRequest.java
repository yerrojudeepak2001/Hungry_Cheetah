package com.foodapp.order.dto;

import lombok.Data;

@Data
public class DeliveryRequest {
    private Long orderId;
    private Long restaurantId;
    private String deliveryAddress;
    private String customerPhone;
    private double latitude;
    private double longitude;
    private String specialInstructions;
}