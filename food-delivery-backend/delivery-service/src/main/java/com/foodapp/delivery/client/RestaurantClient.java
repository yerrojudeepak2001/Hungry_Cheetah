package com.foodapp.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.delivery.dto.RestaurantDeliveryInfo;

@FeignClient(name = "RESTAURANT-SERVICE")
public interface RestaurantClient {
    @GetMapping("/api/restaurants/{restaurantId}/delivery-info")
    RestaurantDeliveryInfo getRestaurantDeliveryInfo(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/{restaurantId}/operating-hours")
    boolean isRestaurantOperating(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/{restaurantId}/location")
    RestaurantLocation getRestaurantLocation(@PathVariable("restaurantId") String restaurantId);
}