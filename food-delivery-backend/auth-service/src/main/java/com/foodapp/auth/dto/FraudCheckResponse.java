package com.foodapp.auth.dto;

import lombok.Data;

@Data
public class FraudCheckResponse {
    private boolean isFraudulent;
    private String riskLevel;
    private String message;
}