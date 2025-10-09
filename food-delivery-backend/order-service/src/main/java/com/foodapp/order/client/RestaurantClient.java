package com.foodapp.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodapp.order.dto.RestaurantResponse;

@FeignClient(name = "RESTAURANT-SERVICE")
public interface RestaurantClient {
    
    @GetMapping("/api/restaurants/{restaurantId}")
    RestaurantResponse getRestaurantDetails(@PathVariable("restaurantId") Long restaurantId);
    
    @GetMapping("/api/restaurants/{restaurantId}/active")
    boolean isRestaurantActive(@PathVariable("restaurantId") Long restaurantId);
}