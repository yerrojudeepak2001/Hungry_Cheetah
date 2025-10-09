package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.DeliveryAnalytics;

@FeignClient(name = "DELIVERY-SERVICE", fallback = DeliveryClientFallback.class)
public interface DeliveryClient {
    @GetMapping("/api/delivery/analytics/performance")
    DeliveryAnalytics getDeliveryPerformanceMetrics(@RequestParam LocalDateTime startDate,
                                                   @RequestParam LocalDateTime endDate);
    
    @GetMapping("/api/delivery/analytics/zones")
    Map<String, Object> getDeliveryZoneMetrics();
    
    @GetMapping("/api/delivery/analytics/drivers")
    List<DriverPerformance> getDriverPerformanceMetrics(@RequestParam String timeframe);
}