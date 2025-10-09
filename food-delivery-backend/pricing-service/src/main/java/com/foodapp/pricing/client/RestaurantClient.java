package com.foodapp.pricing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.pricing.dto.RestaurantPricingData;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/{restaurantId}/pricing")
    RestaurantPricingData getRestaurantPricingData(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/{restaurantId}/menu-items/pricing")
    List<MenuItemPricing> getMenuItemsPricing(@PathVariable("restaurantId") String restaurantId);
    
    @PutMapping("/api/restaurants/{restaurantId}/surge-pricing")
    void updateSurgePricing(@PathVariable("restaurantId") String restaurantId, 
                           @RequestBody SurgePricingUpdate update);
}