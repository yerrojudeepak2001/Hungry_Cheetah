package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayNotificationRequest {
    private String orderId;
    private String userId;
    private int delayMinutes;
    private String reason;
    private long timestamp;
}