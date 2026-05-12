package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DriverStatusUpdate {
    private Long driverId;
    private String orderId;
    private String status; // ACCEPTED, PICKED_UP, ON_THE_WAY, DELIVERED
    private LocalDateTime updateTime;
    private Double latitude;
    private Double longitude;
    private String notes;
    private String photoUrl; // For proof of delivery
}