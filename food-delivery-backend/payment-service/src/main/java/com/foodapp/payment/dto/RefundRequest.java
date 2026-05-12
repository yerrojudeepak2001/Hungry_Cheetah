package com.foodapp.payment.dto;

import lombok.Data;
import java.math.BigDecimal;

@Data
public class RefundRequest {
    private Long paymentId;
    private Long transactionId;
    private BigDecimal amount;
    private String reason;
}