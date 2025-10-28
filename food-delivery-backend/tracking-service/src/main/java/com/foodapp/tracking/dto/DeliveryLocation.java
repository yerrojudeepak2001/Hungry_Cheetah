package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryLocation {
    private String deliveryId;
    private double latitude;
    private double longitude;
    private String address;
    private long timestamp;
}