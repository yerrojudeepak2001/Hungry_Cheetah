package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.foodapp.restaurant.dto.ComplianceCheck;
import com.foodapp.restaurant.dto.RegulationUpdate;

@FeignClient(name = "COMPLIANCE-SERVICE", fallback = ComplianceClientFallback.class)
public interface ComplianceClient {
    @PostMapping("/api/compliance/check")
    ComplianceResult checkCompliance(@RequestBody ComplianceCheck check);
    
    @GetMapping("/api/compliance/restaurant/{restaurantId}/requirements")
    List<ComplianceRequirement> getComplianceRequirements(
        @PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/compliance/updates")
    List<RegulationUpdate> getRegulationUpdates(@RequestParam String region);
    
    @PostMapping("/api/compliance/certificates/verify")
    boolean verifyCertificates(@RequestBody CertificateVerification verification);
}