package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.RestaurantAnalytics;
import java.time.LocalDateTime;

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