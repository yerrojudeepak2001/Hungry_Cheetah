package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaymentAdminInfo {
    private String paymentId;
    private String orderId;
    private String customerId;
    private String customerName;
    private String paymentMethod;
    private String status;
    private double amount;
    private double refundAmount;
    private String currency;
    private String transactionId;
    private String gatewayResponse;
    private String failureReason;
    private boolean isRefunded;
    private LocalDateTime processedAt;
    private LocalDateTime refundedAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}