package com.foodapp.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.search.dto.RestaurantSearchData;
import java.util.List;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/search-data")
    List<RestaurantSearchData> getRestaurantSearchData();
    
    @GetMapping("/api/restaurants/{restaurantId}/menu-items")
    List<MenuItemSearchData> getMenuItems(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/categories")
    List<String> getAllCategories();
}