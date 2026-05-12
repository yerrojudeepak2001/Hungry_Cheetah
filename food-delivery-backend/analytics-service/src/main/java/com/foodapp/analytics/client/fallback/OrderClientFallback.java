package com.foodapp.analytics.client.fallback;

import com.foodapp.analytics.client.OrderClient;
import com.foodapp.analytics.dto.OrderAnalytics;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public OrderAnalytics getOrderAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Fallback implementation
        OrderAnalytics fallback = new OrderAnalytics();
        fallback.setStatus("FALLBACK");
        return fallback;
    }

    @Override
    public OrderAnalytics getRestaurantOrderAnalytics(String restaurantId, LocalDateTime startDate,
            LocalDateTime endDate) {
        // Fallback implementation
        OrderAnalytics fallback = new OrderAnalytics();
        fallback.setRestaurantId(restaurantId);
        fallback.setStatus("FALLBACK");
        return fallback;
    }

    @Override
    public Map<String, Object> getOrderTrends(String timeframe) {
        // Fallback implementation
        return Map.of(
                "status", "fallback",
                "timeframe", timeframe,
                "message", "Order service unavailable");
    }
}