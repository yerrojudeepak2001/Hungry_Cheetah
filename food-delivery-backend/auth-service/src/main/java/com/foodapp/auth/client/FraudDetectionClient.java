package com.foodapp.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.auth.dto.FraudCheckRequest;
import com.foodapp.auth.dto.FraudCheckResponse;
import com.foodapp.auth.dto.SuspiciousActivityReport;

@FeignClient(name = "FRAUD-DETECTION-SERVICE", fallback = FraudCheckClientFallback.class)
public interface FraudDetectionClient {
    @PostMapping("/api/fraud/check/login")
    FraudCheckResponse checkLoginAttempt(@RequestBody FraudCheckRequest request);
    
    @PostMapping("/api/fraud/check/registration")
    FraudCheckResponse checkRegistration(@RequestBody FraudCheckRequest request);
    
    @PostMapping("/api/fraud/report")
    void reportSuspiciousActivity(@RequestBody SuspiciousActivityReport report);
    
    @GetMapping("/api/fraud/user/{userId}/risk-score")
    double getUserRiskScore(@PathVariable("userId") String userId);
}