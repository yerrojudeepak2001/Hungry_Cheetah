package com.foodapp.payment.dto;

import lombok.Data;

@Data
public class PaymentGatewayResponse {
    private String transactionId;
    private String status;
    private String message;
    
    public boolean isSuccessful() {
        return "SUCCESS".equals(status) || "COMPLETED".equals(status);
    }
}