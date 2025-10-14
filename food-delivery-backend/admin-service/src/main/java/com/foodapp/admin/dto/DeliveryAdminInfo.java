package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAdminInfo {
    private String deliveryId;
    private String orderId;
    private String restaurantId;
    private String restaurantName;
    private String customerId;
    private String customerName;
    private String driverId;
    private String driverName;
    private String status;
    private String pickupAddress;
    private String deliveryAddress;
    private LocalDateTime estimatedPickupTime;
    private LocalDateTime estimatedDeliveryTime;
    private LocalDateTime actualPickupTime;
    private LocalDateTime actualDeliveryTime;
    private double deliveryFee;
    private double distance;
    private String notes;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}