package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverAvailability {
    private Long driverId;
    private String status; // AVAILABLE, BUSY, OFFLINE, ON_BREAK
    private LocalDateTime lastActiveTime;
    private Double currentLatitude;
    private Double currentLongitude;
    private String workingArea;
    private LocalDateTime shiftStartTime;
    private LocalDateTime shiftEndTime;
    private boolean acceptingOrders;
}