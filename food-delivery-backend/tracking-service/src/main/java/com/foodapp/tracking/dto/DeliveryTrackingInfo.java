package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTrackingInfo {
    private String deliveryId;
    private String orderId;
    private String status;
    private double currentLatitude;
    private double currentLongitude;
    private String driverId;
    private long lastUpdate;
}