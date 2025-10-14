package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.time.LocalDateTime;

import com.foodapp.restaurant.dto.quality.QualityCheck;
import com.foodapp.restaurant.dto.quality.SafetyReport;
import com.foodapp.restaurant.dto.quality.QualityIncident;
import com.foodapp.restaurant.dto.quality.QualityStandard;
import com.foodapp.restaurant.dto.QualityAudit;
import com.foodapp.restaurant.dto.quality.QualityViolation;
import com.foodapp.restaurant.dto.ImprovementPlan;

@FeignClient(name = "QUALITY-SERVICE", fallback = com.foodapp.restaurant.client.fallback.QualityClientFallback.class)
public interface QualityClient {

    // From old QualityClient
    @PostMapping("/api/quality/checks")
    void submitQualityCheck(@RequestBody QualityCheck check);

    @GetMapping("/api/quality/restaurant/{restaurantId}/reports")
    List<SafetyReport> getRestaurantReports(@PathVariable("restaurantId") String restaurantId);

    @PostMapping("/api/quality/incidents")
    void reportQualityIncident(@RequestBody QualityIncident incident);

    @GetMapping("/api/quality/standards/{restaurantId}")
    List<QualityStandard> getQualityStandards(@PathVariable("restaurantId") String restaurantId);

    // From old QualityServiceClient
    @PostMapping("/api/quality/restaurant/{restaurantId}/audit")
    void submitQualityAudit(@PathVariable("restaurantId") String restaurantId,
                            @RequestBody QualityAudit audit);

    @GetMapping("/api/quality/restaurant/{restaurantId}/violations")
    List<QualityViolation> getQualityViolations(@PathVariable("restaurantId") String restaurantId,
                                                @RequestParam LocalDateTime startDate,
                                                @RequestParam LocalDateTime endDate);

    @PostMapping("/api/quality/restaurant/{restaurantId}/improvement-plan")
    void submitImprovementPlan(@PathVariable("restaurantId") String restaurantId,
                               @RequestBody ImprovementPlan plan);
}
