package com.foodapp.ai.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.ai.dto.OrderPredictionData;
import java.time.LocalDateTime;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/user/{userId}/history")
    List<OrderPredictionData> getUserOrderHistory(@PathVariable("userId") String userId);
    
    @GetMapping("/api/orders/trends")
    Map<String, Object> getOrderTrends(@RequestParam LocalDateTime startDate,
                                     @RequestParam LocalDateTime endDate);
    
    @PostMapping("/api/orders/demand-forecast")
    void updateDemandForecast(@RequestBody Map<String, Object> forecast);
}