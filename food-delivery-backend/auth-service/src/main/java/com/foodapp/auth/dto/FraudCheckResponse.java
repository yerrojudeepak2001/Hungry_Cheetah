package com.foodapp.auth.dto;

import lombok.Data;

@Data
public class FraudCheckResponse {
    private boolean fraudulent;
    private boolean isFraudulent;
    private String reason;
    private String riskLevel;
    private String message;
    private int riskScore;
}