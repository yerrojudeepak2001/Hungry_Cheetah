package com.foodapp.auth.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.auth.dto.RolePermissions;
import com.foodapp.auth.dto.StaffValidationRequest;

import java.util.List;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/restaurants/{restaurantId}/permissions/{role}")
    RolePermissions getRolePermissions(@PathVariable("restaurantId") String restaurantId,
                                     @PathVariable("role") String role);
    
    @PostMapping("/api/restaurants/staff/validate") 
    Boolean validateStaff(@RequestBody StaffValidationRequest request);
                              
    @GetMapping("/api/restaurants/owner/{ownerId}")
    List<String> getRestaurantsByOwner(@PathVariable("ownerId") String ownerId);
}