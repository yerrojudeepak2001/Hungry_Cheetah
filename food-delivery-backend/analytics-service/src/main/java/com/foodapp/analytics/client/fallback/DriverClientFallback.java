package com.foodapp.analytics.client.fallback;

import com.foodapp.analytics.client.DriverClient;
import com.foodapp.analytics.dto.DriverAnalytics;
import com.foodapp.analytics.dto.DriverPerformance;
import com.foodapp.analytics.dto.EarningsAnalysis;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class DriverClientFallback implements DriverClient {

    @Override
    public DriverAnalytics getDriverAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Fallback implementation
        DriverAnalytics fallback = new DriverAnalytics();
        fallback.setIsActive(false);
        return fallback;
    }

    @Override
    public DriverPerformance getDriverPerformance(String driverId, String timeframe) {
        // Fallback implementation
        DriverPerformance fallback = new DriverPerformance();
        fallback.setDriverId(driverId);
        fallback.setCurrentStatus("FALLBACK");
        return fallback;
    }

    @Override
    public Map<String, Object> getAvailabilityTrends(String region, String timeframe) {
        // Fallback implementation
        return Map.of(
                "status", "fallback",
                "region", region,
                "timeframe", timeframe,
                "message", "Driver service unavailable");
    }

    @Override
    public EarningsAnalysis getEarningsAnalysis(String region, LocalDateTime startDate, LocalDateTime endDate) {
        // Fallback implementation
        EarningsAnalysis fallback = new EarningsAnalysis();
        fallback.setPeriod("FALLBACK");
        return fallback;
    }
}