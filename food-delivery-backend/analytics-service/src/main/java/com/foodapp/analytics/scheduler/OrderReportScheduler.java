package com.foodapp.analytics.scheduler;

import com.foodapp.analytics.service.AnalyticsService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class OrderReportScheduler {
    private final AnalyticsService analyticsService;

    public OrderReportScheduler(AnalyticsService analyticsService) {
        this.analyticsService = analyticsService;
    }

    @Scheduled(cron = "0 0 0 * * *") // Daily at midnight
    public void generateDailyOrderReport() {
        analyticsService.generateDailyReport();
    }

    @Scheduled(cron = "0 0 0 * * MON") // Weekly on Monday
    public void generateWeeklyOrderReport() {
        analyticsService.generateWeeklyReport();
    }

    @Scheduled(cron = "0 0 0 1 * *") // Monthly on 1st
    public void generateMonthlyOrderReport() {
        analyticsService.generateMonthlyReport();
    }

    @Scheduled(cron = "0 0/15 * * * *") // Every 15 minutes
    public void updateRealtimeMetrics() {
        analyticsService.updateRealtimeMetrics();
    }
}