package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.DriverAnalytics;
import com.foodapp.analytics.dto.DriverPerformance;
import com.foodapp.analytics.dto.EarningsAnalysis;
import com.foodapp.analytics.client.fallback.DriverClientFallback;
import java.time.LocalDateTime;
import java.util.Map;

@FeignClient(name = "DRIVER-SERVICE", fallback = DriverClientFallback.class)
public interface DriverClient {
    @GetMapping("/api/drivers/analytics")
    DriverAnalytics getDriverAnalytics(@RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate);

    @GetMapping("/api/drivers/performance/{driverId}")
    DriverPerformance getDriverPerformance(@PathVariable("driverId") String driverId,
            @RequestParam String timeframe);

    @GetMapping("/api/drivers/availability/trends")
    Map<String, Object> getAvailabilityTrends(@RequestParam String region,
            @RequestParam String timeframe);

    @GetMapping("/api/drivers/earnings/analysis")
    EarningsAnalysis getEarningsAnalysis(@RequestParam String region,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate);
}