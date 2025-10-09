package com.foodapp.social.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.social.dto.RestaurantSocialData;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/{restaurantId}/social")
    RestaurantSocialData getRestaurantSocialData(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/trending")
    List<RestaurantTrending> getTrendingRestaurants();
    
    @PutMapping("/api/restaurants/{restaurantId}/social-metrics")
    void updateRestaurantSocialMetrics(@PathVariable("restaurantId") String restaurantId, 
                                     @RequestBody SocialMetricsUpdate metrics);
}