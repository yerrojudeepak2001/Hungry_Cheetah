package com.foodapp.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentHistoryDto {
    private String id;
    private String orderId;
    private BigDecimal amount;
    private String currency;
    private String status; // SUCCESS, FAILED, PENDING, REFUNDED
    private String paymentMethod;
    private String transactionId;
    private String gatewayReference;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime paymentDate;
    
    private String description;
    private BigDecimal refundAmount;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime refundDate;
}