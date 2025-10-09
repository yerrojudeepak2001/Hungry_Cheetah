package com.foodapp.search.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.search.dto.MenuIndexData;

@FeignClient(name = "MENU-SERVICE", fallback = MenuClientFallback.class)
public interface MenuClient {
    @GetMapping("/api/menu/changes")
    List<MenuIndexData> getMenuUpdates(@RequestParam LocalDateTime since);
    
    @GetMapping("/api/menu/restaurant/{restaurantId}")
    MenuIndexData getRestaurantMenu(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/menu/categories")
    List<String> getAllCategories();
    
    @GetMapping("/api/menu/trending")
    List<MenuIndexData> getTrendingItems();
}