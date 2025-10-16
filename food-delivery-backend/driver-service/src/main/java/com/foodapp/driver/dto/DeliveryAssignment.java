package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryAssignment {
    private String assignmentId;
    private Long driverId;
    private String orderId;
    private String pickupAddress;
    private String deliveryAddress;
    private Double pickupLatitude;
    private Double pickupLongitude;
    private Double deliveryLatitude;
    private Double deliveryLongitude;
    private LocalDateTime assignedTime;
    private LocalDateTime estimatedPickupTime;
    private LocalDateTime estimatedDeliveryTime;
    private String priority; // HIGH, MEDIUM, LOW
    private String specialInstructions;
    private String customerPhone;
    private String restaurantPhone;
}