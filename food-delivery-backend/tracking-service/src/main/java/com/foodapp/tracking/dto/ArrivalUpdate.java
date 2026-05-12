package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArrivalUpdate {
    private String orderId;
    private String driverId;
    private double latitude;
    private double longitude;
    private long arrivalTime;
    private String status;
}