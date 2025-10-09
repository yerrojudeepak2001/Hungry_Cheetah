package com.foodapp.loyalty.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.loyalty.dto.OrderPointsInfo;
import java.time.LocalDateTime;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/{orderId}/points-info")
    OrderPointsInfo getOrderPointsInfo(@PathVariable("orderId") String orderId);
    
    @GetMapping("/api/orders/user/{userId}/points-eligible")
    List<OrderPointsInfo> getPointsEligibleOrders(
        @PathVariable("userId") String userId,
        @RequestParam LocalDateTime startDate,
        @RequestParam LocalDateTime endDate);
        
    @PutMapping("/api/orders/{orderId}/points-awarded")
    void updateOrderPointsAwarded(@PathVariable("orderId") String orderId, @RequestParam int points);
}