package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryStatus {
    private String deliveryId;
    private String orderId;
    private Long driverId;
    private String status; // ASSIGNED, PICKED_UP, OUT_FOR_DELIVERY, DELIVERED, CANCELLED
    private LocalDateTime statusTime;
    private Double currentLatitude;
    private Double currentLongitude;
    private String notes;
    private Integer estimatedMinutesRemaining;
}