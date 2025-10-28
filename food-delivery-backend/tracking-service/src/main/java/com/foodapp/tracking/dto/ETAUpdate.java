package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ETAUpdate {
    private String orderId;
    private int estimatedMinutes;
    private long updatedAt;
    private String reason;
}