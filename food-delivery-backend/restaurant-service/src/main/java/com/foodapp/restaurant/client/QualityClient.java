package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.foodapp.restaurant.dto.QualityCheck;
import com.foodapp.restaurant.dto.SafetyReport;

@FeignClient(name = "QUALITY-SERVICE", fallback = QualityClientFallback.class)
public interface QualityClient {
    @PostMapping("/api/quality/checks")
    void submitQualityCheck(@RequestBody QualityCheck check);
    
    @GetMapping("/api/quality/restaurant/{restaurantId}/reports")
    List<SafetyReport> getRestaurantReports(@PathVariable("restaurantId") String restaurantId);
    
    @PostMapping("/api/quality/incidents")
    void reportQualityIncident(@RequestBody QualityIncident incident);
    
    @GetMapping("/api/quality/standards/{restaurantId}")
    List<QualityStandard> getQualityStandards(@PathVariable("restaurantId") String restaurantId);
}