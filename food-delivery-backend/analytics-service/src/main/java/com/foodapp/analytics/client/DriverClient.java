package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.DriverAnalytics;

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