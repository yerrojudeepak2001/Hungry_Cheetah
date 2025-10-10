package com.foodapp.auth.client;

import com.foodapp.auth.dto.FraudCheckRequest;
import com.foodapp.auth.dto.FraudCheckResponse;
import com.foodapp.auth.dto.SuspiciousActivityReport;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FraudCheckClientFallback implements FraudDetectionClient {
    
    @Override
    public FraudCheckResponse checkForFraud(FraudCheckRequest request) {
        FraudCheckResponse response = new FraudCheckResponse();
        response.setFraudulent(false);
        response.setRiskLevel("LOW");
        response.setMessage("Fraud detection service unavailable");
        return response;
    }
    
    @Override
    public FraudCheckResponse performRealTimeCheck(FraudCheckRequest request) {
        return checkForFraud(request);
    }
    
    @Override
    public List<SuspiciousActivityReport> getSuspiciousActivities(String userId) {
        return List.of();
    }
}