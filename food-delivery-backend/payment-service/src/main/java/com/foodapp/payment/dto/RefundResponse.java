package com.foodapp.payment.dto;

import lombok.Data;

@Data
public class RefundResponse {
    private String refundId;
    private String status;
    private String message;
}