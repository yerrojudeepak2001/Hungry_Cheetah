package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderTrackingInfo {
    private String orderId;
    private String status;
    private double currentLatitude;
    private double currentLongitude;
    private int estimatedDeliveryMinutes;
    private String driverId;
    private String driverName;
    private String driverPhone;
}