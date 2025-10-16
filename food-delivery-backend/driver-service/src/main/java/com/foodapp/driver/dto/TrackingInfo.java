package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrackingInfo {
    private String trackingId;
    private String orderId;
    private Long driverId;
    private String currentStatus;
    private Double currentLatitude;
    private Double currentLongitude;
    private LocalDateTime lastUpdate;
    private Integer estimatedArrivalMinutes;
    private String driverName;
    private String driverPhone;
    private String vehicleInfo;
}