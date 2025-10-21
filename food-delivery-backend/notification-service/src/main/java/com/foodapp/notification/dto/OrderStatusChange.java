package com.foodapp.notification.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderStatusChange {
    private String orderId;
    private String fromStatus;
    private String toStatus;
    private LocalDateTime changedAt;
    private String changedBy;
    private String reason;
}