package com.foodapp.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.auth.dto.RolePermissions;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/{restaurantId}/staff/{userId}/permissions")
    RolePermissions getStaffPermissions(@PathVariable("restaurantId") String restaurantId,
                                      @PathVariable("userId") String userId);
    
    @PostMapping("/api/restaurants/{restaurantId}/staff/validate")
    boolean validateStaffAccess(@PathVariable("restaurantId") String restaurantId,
                              @RequestBody StaffValidationRequest request);
                              
    @GetMapping("/api/restaurants/owner/{userId}")
    List<String> getOwnedRestaurants(@PathVariable("userId") String userId);
}