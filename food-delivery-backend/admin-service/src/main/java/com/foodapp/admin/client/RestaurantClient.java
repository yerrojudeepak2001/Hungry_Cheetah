package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.*;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "RESTAURANT-SERVICE", fallback = RestaurantClientFallback.class)
public interface RestaurantClient {
    @GetMapping("/api/admin/restaurants")
    List<RestaurantAdminInfo> getAllRestaurants(@RequestParam int page, @RequestParam int size);
    
    @PutMapping("/api/admin/restaurants/{restaurantId}/status")
    void updateRestaurantStatus(@PathVariable("restaurantId") String restaurantId, 
                              @RequestParam String status);
    
    @GetMapping("/api/admin/restaurants/audit-logs")
    List<RestaurantAuditLog> getRestaurantAuditLogs(@RequestParam LocalDateTime startDate, 
                                                   @RequestParam LocalDateTime endDate);
}