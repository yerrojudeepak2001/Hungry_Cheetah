package com.foodapp.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodapp.user.dto.RestaurantResponse;
import java.util.List;

@FeignClient(name = "RESTAURANT-SERVICE")
public interface RestaurantClient {

    // Get favorite restaurants
    @GetMapping("/api/restaurants/user/{userId}/favorites")
    List<RestaurantResponse> getUserFavoriteRestaurants(@PathVariable("userId") String userId);

    // Get recent restaurants
    @GetMapping("/api/restaurants/user/{userId}/recent")
    List<RestaurantResponse> getUserRecentRestaurants(@PathVariable("userId") String userId);

    // Add restaurant to favorites
    @PostMapping("/api/restaurants/user/{userId}/favorites/{restaurantId}")
    void addRestaurantToFavorites(
            @PathVariable("userId") String userId,
            @PathVariable("restaurantId") String restaurantId
    );

    // Remove restaurant from favorites
    @DeleteMapping("/api/restaurants/user/{userId}/favorites/{restaurantId}")
    void removeRestaurantFromFavorites(
            @PathVariable("userId") String userId,
            @PathVariable("restaurantId") String restaurantId
    );
}
