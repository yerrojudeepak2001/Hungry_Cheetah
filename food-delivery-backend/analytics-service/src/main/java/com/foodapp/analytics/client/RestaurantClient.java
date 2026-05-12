package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.RestaurantAnalytics;
import com.foodapp.analytics.client.fallback.RestaurantClientFallback;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/analytics")
    RestaurantAnalytics getRestaurantAnalytics(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate);

    @GetMapping("/api/restaurants/performance")
    Map<String, Object> getPerformanceMetrics(
            @RequestParam String restaurantId,
            @RequestParam String timeframe);

    @GetMapping("/api/restaurants/popular-items")
    List<Map<String, Object>> getPopularItems(
            @RequestParam String timeframe);
}