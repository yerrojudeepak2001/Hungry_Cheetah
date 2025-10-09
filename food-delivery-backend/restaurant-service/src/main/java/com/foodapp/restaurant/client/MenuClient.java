package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.restaurant.dto.MenuData;
import com.foodapp.restaurant.dto.MenuItemAvailability;

@FeignClient(name = "MENU-SERVICE", fallback = MenuClientFallback.class)
public interface MenuClient {
    @GetMapping("/api/menu/restaurant/{restaurantId}")
    MenuData getRestaurantMenu(@PathVariable("restaurantId") String restaurantId);
    
    @PutMapping("/api/menu/items/{itemId}/availability")
    void updateItemAvailability(@PathVariable("itemId") String itemId,
                              @RequestBody MenuItemAvailability availability);
    
    @GetMapping("/api/menu/restaurant/{restaurantId}/specials")
    List<MenuData> getDailySpecials(@PathVariable("restaurantId") String restaurantId);
    
    @PostMapping("/api/menu/sync")
    void syncMenuUpdates(@RequestBody MenuSyncRequest syncRequest);
}