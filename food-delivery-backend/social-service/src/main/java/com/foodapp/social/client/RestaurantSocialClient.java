package com.foodapp.social.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.social.dto.RestaurantSocialContent;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantSocialClient {
    @GetMapping("/api/restaurants/{restaurantId}/social")
    RestaurantSocialContent getRestaurantSocialContent(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/trending")
    List<RestaurantSocialContent> getTrendingRestaurants(@RequestParam String location);
    
    @GetMapping("/api/restaurants/{restaurantId}/events")
    List<SocialEvent> getRestaurantEvents(@PathVariable("restaurantId") String restaurantId);
    
    @PostMapping("/api/restaurants/{restaurantId}/social/engagement")
    void updateSocialEngagement(@PathVariable("restaurantId") String restaurantId,
                              @RequestBody EngagementMetrics metrics);
}