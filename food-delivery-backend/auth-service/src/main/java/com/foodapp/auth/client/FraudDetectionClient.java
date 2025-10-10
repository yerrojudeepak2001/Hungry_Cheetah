package com.foodapp.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.auth.dto.FraudCheckRequest;
import com.foodapp.auth.dto.FraudCheckResponse;
import com.foodapp.auth.dto.SuspiciousActivityReport;
<<<<<<< HEAD
=======

import java.util.List;
>>>>>>> version1.4

@FeignClient(name = "FRAUD-DETECTION-SERVICE", fallback = FraudCheckClientFallback.class)
public interface FraudDetectionClient {
    @PostMapping("/api/fraud/check")
    FraudCheckResponse checkForFraud(@RequestBody FraudCheckRequest request);
    
    @PostMapping("/api/fraud/check/realtime")
    FraudCheckResponse performRealTimeCheck(@RequestBody FraudCheckRequest request);
    
    @GetMapping("/api/fraud/user/{userId}/suspicious")
    List<SuspiciousActivityReport> getSuspiciousActivities(@PathVariable("userId") String userId);
}