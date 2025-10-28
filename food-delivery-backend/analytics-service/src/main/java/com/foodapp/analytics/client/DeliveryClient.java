package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.DeliveryAnalytics;
import com.foodapp.analytics.dto.DriverPerformance;
import com.foodapp.analytics.client.fallback.DeliveryClientFallback;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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