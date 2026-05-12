package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@FeignClient(
    name = "RESTAURANT-SERVICE", 
    url = "${services.restaurant-service.url:http://localhost:8084}",
    fallback = RestaurantClientFallback.class
)
public interface RestaurantClient {
    
    // Statistics endpoints
    @GetMapping("/api/v1/admin/restaurants/stats")
    ResponseEntity<Map<String, Long>> getRestaurantStats();
    
    // Restaurant management endpoints
    @GetMapping("/api/v1/admin/restaurants")
    ResponseEntity<Page<RestaurantAdminInfo>> getAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search);

    @GetMapping("/api/v1/admin/restaurants/{restaurantId}")
    ResponseEntity<RestaurantAdminInfo> getRestaurantById(@PathVariable String restaurantId);

    @PutMapping("/api/v1/admin/restaurants/{restaurantId}/status")
    ResponseEntity<Void> updateRestaurantStatus(
            @PathVariable String restaurantId,
            @RequestParam String status,
            @RequestParam(required = false) String reason);

    @PostMapping("/api/v1/admin/restaurants/{restaurantId}/approve")
    ResponseEntity<Void> approveRestaurant(
            @PathVariable String restaurantId,
            @RequestBody ApproveRestaurantRequest request);

    @PostMapping("/api/v1/admin/restaurants/{restaurantId}/reject")
    ResponseEntity<Void> rejectRestaurant(
            @PathVariable String restaurantId,
            @RequestBody RejectRestaurantRequest request);

    @PostMapping("/api/v1/admin/restaurants/{restaurantId}/suspend")
    ResponseEntity<Void> suspendRestaurant(
            @PathVariable String restaurantId,
            @RequestBody SuspendRestaurantRequest request);

    @PostMapping("/api/v1/admin/restaurants/{restaurantId}/reactivate")
    ResponseEntity<Void> reactivateRestaurant(@PathVariable String restaurantId);

    @GetMapping("/api/v1/admin/restaurants/{restaurantId}/audit-logs")
    ResponseEntity<List<RestaurantAuditLog>> getRestaurantAuditLogs(
            @PathVariable String restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @DeleteMapping("/api/v1/admin/restaurants/{restaurantId}")
    ResponseEntity<Void> deleteRestaurant(@PathVariable String restaurantId);

    @PostMapping("/api/v1/admin/restaurants/cache/clear")
    ResponseEntity<Void> clearCache();
    
    // Legacy methods for backward compatibility
    @Deprecated
    @GetMapping("/api/admin/restaurants")
    List<RestaurantAdminInfo> getAllRestaurants(@RequestParam int page, @RequestParam int size);
    
    @Deprecated
    @GetMapping("/api/admin/restaurants/audit-logs")
    List<RestaurantAuditLog> getRestaurantAuditLogs(@RequestParam LocalDateTime startDate, 
                                                   @RequestParam LocalDateTime endDate);

    // Request DTOs
    class ApproveRestaurantRequest {
        private String comments;
        public String getComments() { return comments; }
        public void setComments(String comments) { this.comments = comments; }
    }

    class RejectRestaurantRequest {
        private String reason;
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }

    class SuspendRestaurantRequest {
        private String reason;
        private Integer duration;
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
    }
}