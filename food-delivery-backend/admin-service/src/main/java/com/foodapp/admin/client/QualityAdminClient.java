package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@FeignClient(name = "QUALITY-SERVICE", fallback = QualityClientFallback.class)
public interface QualityAdminClient {
    @GetMapping("/api/quality/admin/reports")
    List<QualityReport> getQualityReports(@RequestParam LocalDateTime startDate,
                                         @RequestParam LocalDateTime endDate);
    
    @GetMapping("/api/quality/admin/violations")
    List<QualityViolation> getQualityViolations(@RequestParam String severity,
                                               @RequestParam String status);
    
    @PutMapping("/api/quality/admin/violations/{violationId}")
    void updateViolationStatus(@PathVariable("violationId") String violationId,
                             @RequestBody ViolationStatusUpdate update);
    
    @GetMapping("/api/quality/admin/standards/compliance")
    Map<String, ComplianceStats> getComplianceStatistics(@RequestParam String region);
}