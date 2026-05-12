package com.foodapp.analytics.client.fallback;

import com.foodapp.analytics.client.RestaurantClient;
import com.foodapp.analytics.dto.RestaurantAnalytics;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class RestaurantClientFallback implements RestaurantClient {

    @Override
    public RestaurantAnalytics getRestaurantAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Fallback implementation
        RestaurantAnalytics fallback = new RestaurantAnalytics();
        fallback.setIsActive(false);
        return fallback;
    }

    @Override
    public Map<String, Object> getPerformanceMetrics(String restaurantId, String timeframe) {
        // Fallback implementation
        return Map.of(
                "status", "fallback",
                "restaurantId", restaurantId,
                "timeframe", timeframe,
                "message", "Restaurant service unavailable");
    }

    @Override
    public List<Map<String, Object>> getPopularItems(String timeframe) {
        // Fallback implementation
        return Collections.emptyList();
    }
}