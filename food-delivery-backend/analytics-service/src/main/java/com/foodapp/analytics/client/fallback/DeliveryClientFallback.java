package com.foodapp.analytics.client.fallback;

import com.foodapp.analytics.client.DeliveryClient;
import com.foodapp.analytics.dto.DeliveryAnalytics;
import com.foodapp.analytics.dto.DriverPerformance;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Collections;

@Component
public class DeliveryClientFallback implements DeliveryClient {

    @Override
    public DeliveryAnalytics getDeliveryAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Fallback implementation
        DeliveryAnalytics fallback = new DeliveryAnalytics();
        fallback.setStatus("FALLBACK");
        return fallback;
    }

    @Override
    public Map<String, Object> getDeliveryMetrics() {
        // Fallback implementation
        return Map.of(
                "status", "fallback",
                "message", "Delivery service unavailable");
    }

    @Override
    public List<DriverPerformance> getDriverPerformance() {
        // Fallback implementation
        return Collections.emptyList();
    }
}