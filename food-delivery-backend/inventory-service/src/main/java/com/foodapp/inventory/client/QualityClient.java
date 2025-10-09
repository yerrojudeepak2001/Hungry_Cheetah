package com.foodapp.inventory.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.inventory.dto.QualityCheck;

@FeignClient(name = "QUALITY-SERVICE", fallback = QualityClientFallback.class)
public interface QualityClient {
    @PostMapping("/api/quality/inventory/check")
    QualityCheckResult checkInventoryQuality(@RequestBody QualityCheck check);
    
    @PostMapping("/api/quality/inventory/incident")
    void reportQualityIncident(@RequestBody QualityIncident incident);
    
    @GetMapping("/api/quality/inventory/standards")
    List<QualityStandard> getInventoryStandards();
    
    @GetMapping("/api/quality/inventory/expiry-alerts")
    List<ExpiryAlert> getExpiryAlerts(@RequestParam String locationId);
}