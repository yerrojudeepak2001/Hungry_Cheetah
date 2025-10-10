package com.foodapp.auth.client;

import com.foodapp.auth.dto.FraudCheckRequest;
import com.foodapp.auth.dto.FraudCheckResponse;
import com.foodapp.auth.dto.SuspiciousActivityReport;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class FraudCheckClientFallback implements FraudDetectionClient {
    public FraudCheckResponse checkLoginAttempt(FraudCheckRequest request) {
        FraudCheckResponse response = new FraudCheckResponse();
        // Return safe fallback response
        return response;
    }

    public FraudCheckResponse checkRegistration(FraudCheckRequest request) {
        FraudCheckResponse response = new FraudCheckResponse();
        // Return safe fallback response
        return response;
    }

    public void reportSuspiciousActivity(SuspiciousActivityReport report) {
        // Log fallback - do nothing
    }

    public double getUserRiskScore(String userId) {
        return 0.0; // Default low risk score in fallback
    }
    
    public FraudCheckResponse checkForFraud(FraudCheckRequest request) {
        FraudCheckResponse response = new FraudCheckResponse();
        // Return safe fallback response
        return response;
    }
    
    public FraudCheckResponse performRealTimeCheck(FraudCheckRequest request) {
        return checkForFraud(request);
    }
    
    public List<SuspiciousActivityReport> getSuspiciousActivities(String userId) {
        return List.of();
    }
}