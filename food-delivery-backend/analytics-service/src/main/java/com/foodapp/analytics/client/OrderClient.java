package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.OrderAnalytics;
import com.foodapp.analytics.client.fallback.OrderClientFallback;
import java.time.LocalDateTime;
import java.util.Map;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/analytics")
    OrderAnalytics getOrderAnalytics(
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate);

    @GetMapping("/api/orders/restaurant/{restaurantId}/analytics")
    OrderAnalytics getRestaurantOrderAnalytics(
            @PathVariable("restaurantId") String restaurantId,
            @RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate);

    @GetMapping("/api/orders/trends")
    Map<String, Object> getOrderTrends(@RequestParam String timeframe);
}