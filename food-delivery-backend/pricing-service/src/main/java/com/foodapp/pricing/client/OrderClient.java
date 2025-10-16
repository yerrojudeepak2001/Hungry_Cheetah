package com.foodapp.pricing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.pricing.dto.OrderPricingInfo;
import com.foodapp.pricing.dto.PricingUpdate;
import com.foodapp.pricing.client.fallback.OrderClientFallback;
import java.util.Map;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/{orderId}/pricing")
    OrderPricingInfo getOrderPricingInfo(@PathVariable("orderId") String orderId);
    
    @PutMapping("/api/orders/{orderId}/pricing")
    void updateOrderPricing(@PathVariable("orderId") String orderId, 
                          @RequestBody PricingUpdate update);
    
    @GetMapping("/api/orders/demand-metrics")
    Map<String, Object> getDemandMetrics(@RequestParam String timeframe);
    
    @GetMapping("/api/orders/active-count")
    int getActiveOrdersCount(@RequestParam String area);
}