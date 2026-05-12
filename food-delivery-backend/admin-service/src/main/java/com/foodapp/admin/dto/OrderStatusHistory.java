package com.foodapp.admin.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatusHistory {
    private String id;
    private String orderId;
    private String previousStatus;
    private String newStatus;
    private String changedBy;
    private String reason;
    private String comments;
    private LocalDateTime changedAt;
}