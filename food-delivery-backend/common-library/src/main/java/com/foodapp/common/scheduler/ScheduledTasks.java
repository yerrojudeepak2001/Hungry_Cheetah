package com.foodapp.common.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ScheduledTasks {

    @Scheduled(fixedRate = 300000) // every 5 minutes
    public void healthCheck() {
        log.info("Performing scheduled health check");
    }

    @Scheduled(cron = "0 0 0 * * *") // midnight every day
    public void dailyMaintenance() {
        log.info("Performing daily maintenance");
    }

    @Scheduled(fixedDelayString = "${app.scheduler.metrics-collection-delay:60000}")
    public void collectMetrics() {
        log.info("Collecting metrics");
    }
}