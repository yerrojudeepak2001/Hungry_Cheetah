package com.foodapp.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.cart.dto.PricingRequest;
import com.foodapp.cart.dto.PriceBreakdown;

@FeignClient(name = "PRICING-SERVICE", fallback = PricingClientFallback.class)
public interface PricingClient {
    @PostMapping("/api/pricing/calculate")
    PriceBreakdown calculatePrice(@RequestBody PricingRequest request);
    
    @GetMapping("/api/pricing/discounts")
    List<Discount> getApplicableDiscounts(@RequestParam String userId);
    
    @GetMapping("/api/pricing/delivery-fee")
    double getDeliveryFee(@RequestParam String restaurantId, 
                         @RequestParam String deliveryLocation);
}