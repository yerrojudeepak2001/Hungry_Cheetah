package com.foodapp.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.cart.dto.MenuItemValidation;
import java.util.List;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = com.foodapp.cart.client.fallback.RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/menu-items/{itemId}/validate")
    MenuItemValidation validateMenuItem(@PathVariable("itemId") String itemId);
    
    @GetMapping("/api/restaurants/{restaurantId}/menu-items/{itemId}/validate")
    MenuItemValidation validateMenuItem(@PathVariable("restaurantId") String restaurantId, 
                                      @PathVariable("itemId") String itemId);
    
    @GetMapping("/api/restaurants/{restaurantId}/availability")
    boolean checkRestaurantAvailability(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/restaurants/menu-items/bulk-validate")
    List<MenuItemValidation> validateMenuItems(@RequestParam List<String> itemIds);
    
    @GetMapping("/api/restaurants/{restaurantId}/menu-items/bulk-validate")
    List<MenuItemValidation> validateMenuItems(@PathVariable("restaurantId") String restaurantId, 
                                             @RequestParam List<String> itemIds);
}