package com.foodapp.auth.dto;

public class FraudCheckResponse {
    private boolean fraudulent;
    private String reason;
    private int riskScore;
    
    // Getters and setters
    public boolean isFraudulent() {
        return fraudulent;
    }

    public void setFraudulent(boolean fraudulent) {
        this.fraudulent = fraudulent;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public int getRiskScore() {
        return riskScore;
    }

    public void setRiskScore(int riskScore) {
        this.riskScore = riskScore;
    }
import lombok.Data;

@Data
public class FraudCheckResponse {
    private boolean isFraudulent;
    private String riskLevel;
    private String message;
}