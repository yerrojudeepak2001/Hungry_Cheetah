package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverStatus {
    private Long driverId;
    private String status; // ONLINE, OFFLINE, BUSY, ON_BREAK
    private LocalDateTime lastStatusChange;
    private String currentOrderId;
    private Integer totalDeliveries;
    private Double averageRating;
    private LocalDateTime shiftStartTime;
}