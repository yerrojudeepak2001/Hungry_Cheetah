package com.foodapp.admin.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderAdminInfo {
    private String orderId;
    private String userId;
    private String userName;
    private String restaurantId;
    private String restaurantName;
    private String status;
    private double totalAmount;
    private double discount;
    private double deliveryFee;
    private double tax;
    private String paymentMethod;
    private String paymentStatus;
    private LocalDateTime orderDate;
    private LocalDateTime deliveryTime;
    private String deliveryAddress;
    private String deliveryPersonId;
    private String deliveryPersonName;
    private List<OrderItemInfo> items;
    private String specialInstructions;
    private String cancellationReason;
    private double rating;
    private String feedback;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OrderItemInfo {
        private String itemId;
        private String itemName;
        private int quantity;
        private double price;
        private double totalPrice;
        private List<String> customizations;
    }
}