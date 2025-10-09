package com.foodapp.promotion.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.promotion.dto.OrderPromotionInfo;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/{orderId}/promotion-eligibility")
    OrderPromotionInfo getOrderPromotionEligibility(@PathVariable("orderId") String orderId);
    
    @PostMapping("/api/orders/{orderId}/apply-promotion")
    void applyPromotion(@PathVariable("orderId") String orderId, 
                        @RequestBody PromotionApplication promotion);
    
    @GetMapping("/api/orders/promotion-stats")
    Map<String, Object> getPromotionStats(@RequestParam String promotionId,
                                        @RequestParam LocalDateTime startDate,
                                        @RequestParam LocalDateTime endDate);
}