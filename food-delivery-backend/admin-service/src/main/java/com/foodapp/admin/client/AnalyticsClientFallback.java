package com.foodapp.admin.client;

import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Map;

@Component
public class AnalyticsClientFallback implements AnalyticsAdminClient {
    
    @Override
    public DashboardData getDashboardAnalytics(String timeframe) {
        return new DashboardData(); // Return empty dashboard data
    }
    
    @Override
    public AnalyticsReport getCustomReport(ReportRequest request) {
        return new AnalyticsReport(); // Return empty report
    }
    
    @Override
    public Map<String, KPIMetrics> getKPIMetrics(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.emptyMap();
    }
    
    @Override
    public void scheduleReport(ReportSchedule schedule) {
        // No-op fallback
    }
}