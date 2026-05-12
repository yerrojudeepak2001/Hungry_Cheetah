package com.foodapp.payment.dto;

import lombok.Data;

@Data
public class InvoiceGenerationRequest {
    private Long orderId;
    private Long userId;
}