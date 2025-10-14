package com.foodapp.admin.controller;

import com.foodapp.admin.dto.ApiResponse;
import com.foodapp.admin.dto.RestaurantAdminInfo;
import com.foodapp.admin.dto.RestaurantAuditLog;
import com.foodapp.admin.service.RestaurantServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/restaurants")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class RestaurantManagementController {

    private final RestaurantServiceClient restaurantServiceClient;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<RestaurantStats> getRestaurantStats() {
        RestaurantStats stats = RestaurantStats.builder()
            .totalRestaurants(restaurantServiceClient.getTotalRestaurants())
            .activeRestaurants(restaurantServiceClient.getActiveRestaurants())
            .pendingApprovals(restaurantServiceClient.getPendingApprovals())
            .suspendedRestaurants(restaurantServiceClient.getSuspendedRestaurants())
            .build();
        
        return new ApiResponse<>(true, "Restaurant statistics retrieved successfully", stats);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<RestaurantAdminInfo>> getAllRestaurants(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        
        Page<RestaurantAdminInfo> restaurants = restaurantServiceClient.getAllRestaurants(page, size, sortBy, sortDirection, status, search);
        return new ApiResponse<>(true, "Restaurants retrieved successfully", restaurants);
    }

    @GetMapping("/{restaurantId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<RestaurantAdminInfo> getRestaurantById(@PathVariable String restaurantId) {
        RestaurantAdminInfo restaurant = restaurantServiceClient.getRestaurantById(restaurantId);
        return new ApiResponse<>(true, "Restaurant retrieved successfully", restaurant);
    }

    @PutMapping("/{restaurantId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateRestaurantStatus(
            @PathVariable String restaurantId,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {
        
        restaurantServiceClient.updateRestaurantStatus(restaurantId, status, reason);
        return new ApiResponse<>(true, "Restaurant status updated successfully", null);
    }

    @PostMapping("/{restaurantId}/approve")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> approveRestaurant(
            @PathVariable String restaurantId,
            @RequestBody ApproveRestaurantRequest request) {
        
        restaurantServiceClient.approveRestaurant(restaurantId, request.getComments());
        return new ApiResponse<>(true, "Restaurant approved successfully", null);
    }

    @PostMapping("/{restaurantId}/reject")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> rejectRestaurant(
            @PathVariable String restaurantId,
            @RequestBody RejectRestaurantRequest request) {
        
        restaurantServiceClient.rejectRestaurant(restaurantId, request.getReason());
        return new ApiResponse<>(true, "Restaurant rejected successfully", null);
    }

    @PostMapping("/{restaurantId}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> suspendRestaurant(
            @PathVariable String restaurantId,
            @RequestBody SuspendRestaurantRequest request) {
        
        restaurantServiceClient.suspendRestaurant(restaurantId, request.getReason(), request.getDuration());
        return new ApiResponse<>(true, "Restaurant suspended successfully", null);
    }

    @PostMapping("/{restaurantId}/reactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> reactivateRestaurant(@PathVariable String restaurantId) {
        restaurantServiceClient.reactivateRestaurant(restaurantId);
        return new ApiResponse<>(true, "Restaurant reactivated successfully", null);
    }

    @GetMapping("/{restaurantId}/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<RestaurantAuditLog>> getRestaurantAuditLogs(
            @PathVariable String restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<RestaurantAuditLog> auditLogs = restaurantServiceClient.getRestaurantAuditLogs(restaurantId, page, size);
        return new ApiResponse<>(true, "Restaurant audit logs retrieved successfully", auditLogs);
    }

    @DeleteMapping("/{restaurantId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<Void> deleteRestaurant(@PathVariable String restaurantId) {
        restaurantServiceClient.deleteRestaurant(restaurantId);
        return new ApiResponse<>(true, "Restaurant deleted successfully", null);
    }

    @PostMapping("/cache/clear")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> clearRestaurantCache() {
        restaurantServiceClient.clearCache();
        return new ApiResponse<>(true, "Restaurant service cache cleared successfully", null);
    }

    // Inner classes for request/response DTOs
    @lombok.Data
    @lombok.Builder
    public static class RestaurantStats {
        private long totalRestaurants;
        private long activeRestaurants;
        private long pendingApprovals;
        private long suspendedRestaurants;
    }

    @lombok.Data
    public static class ApproveRestaurantRequest {
        private String comments;
    }

    @lombok.Data
    public static class RejectRestaurantRequest {
        private String reason;
    }

    @lombok.Data
    public static class SuspendRestaurantRequest {
        private String reason;
        private Integer duration; // days
    }
}