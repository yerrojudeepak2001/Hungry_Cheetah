package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusUpdate {
    private String updateId;
    private String orderId;
    private Long driverId;
    private String previousStatus;
    private String newStatus;
    private LocalDateTime updateTime;
    private String notes;
    private Double latitude;
    private Double longitude;
}