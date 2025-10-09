package com.foodapp.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.cart.dto.MenuItemValidation;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/menu-items/{itemId}/validate")
    MenuItemValidation validateMenuItem(@PathVariable("itemId") String itemId);
    
    @GetMapping("/api/restaurants/{restaurantId}/availability")
    boolean checkRestaurantAvailability(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/menu-items/bulk-validate")
    List<MenuItemValidation> validateMenuItems(@RequestParam List<String> itemIds);
}