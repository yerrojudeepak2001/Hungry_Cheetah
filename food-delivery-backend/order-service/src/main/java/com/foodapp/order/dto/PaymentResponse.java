package com.foodapp.order.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PaymentResponse {
    private Long paymentId;
    private Long orderId;
    private String status;
    private BigDecimal amount;
    private String transactionId;
    private LocalDateTime timestamp;
    private String message;
}