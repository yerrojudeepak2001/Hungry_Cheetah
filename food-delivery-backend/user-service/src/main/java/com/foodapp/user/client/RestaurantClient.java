package com.foodapp.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodapp.user.dto.RestaurantResponse;
import java.util.List;

@FeignClient(name = "RESTAURANT-SERVICE")
public interface RestaurantClient {
    @GetMapping("/api/restaurants/user/{userId}/favorites")
    List<RestaurantResponse> getUserFavoriteRestaurants(@PathVariable("userId") String userId);
    
    @GetMapping("/api/restaurants/user/{userId}/recent")
    List<RestaurantResponse> getUserRecentRestaurants(@PathVariable("userId") String userId);
}