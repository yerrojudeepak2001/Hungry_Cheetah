package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.*;
import java.time.LocalDateTime;
import java.util.Map;

@FeignClient(name = "ANALYTICS-SERVICE", fallback = AnalyticsClientFallback.class)
public interface AnalyticsAdminClient {
    @GetMapping("/api/analytics/admin/dashboard")
    DashboardData getDashboardAnalytics(@RequestParam String timeframe);
    
    @GetMapping("/api/analytics/admin/reports/custom")
    AnalyticsReport getCustomReport(@RequestBody ReportRequest request);
    
    @GetMapping("/api/analytics/admin/kpis")
    Map<String, KPIMetrics> getKPIMetrics(@RequestParam LocalDateTime startDate,
                                         @RequestParam LocalDateTime endDate);
    
    @PostMapping("/api/analytics/admin/reports/schedule")
    void scheduleReport(@RequestBody ReportSchedule schedule);
}