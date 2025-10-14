package com.foodapp.admin.client;

import com.foodapp.admin.dto.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Collections;
import java.util.HashMap;

@Component
public class RestaurantClientFallback implements RestaurantClient {
    
    @Override
    public ResponseEntity<Map<String, Long>> getRestaurantStats() {
        Map<String, Long> fallbackStats = new HashMap<>();
        fallbackStats.put("totalRestaurants", 0L);
        fallbackStats.put("activeRestaurants", 0L);
        fallbackStats.put("pendingApprovals", 0L);
        fallbackStats.put("suspendedRestaurants", 0L);
        return ResponseEntity.ok(fallbackStats);
    }

    @Override
    public ResponseEntity<Page<RestaurantAdminInfo>> getAllRestaurants(int page, int size, String sortBy, String sortDirection, String status, String search) {
        return ResponseEntity.ok(new PageImpl<>(Collections.emptyList()));
    }

    @Override
    public ResponseEntity<RestaurantAdminInfo> getRestaurantById(String restaurantId) {
        return ResponseEntity.ok(new RestaurantAdminInfo());
    }

    @Override
    public ResponseEntity<Void> updateRestaurantStatus(String restaurantId, String status, String reason) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> approveRestaurant(String restaurantId, RestaurantClient.ApproveRestaurantRequest request) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> rejectRestaurant(String restaurantId, RestaurantClient.RejectRestaurantRequest request) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> suspendRestaurant(String restaurantId, RestaurantClient.SuspendRestaurantRequest request) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> reactivateRestaurant(String restaurantId) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<RestaurantAuditLog>> getRestaurantAuditLogs(String restaurantId, int page, int size) {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @Override
    public ResponseEntity<Void> deleteRestaurant(String restaurantId) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> clearCache() {
        return ResponseEntity.ok().build();
    }
    
    // Legacy methods for backward compatibility
    @Override
    public List<RestaurantAdminInfo> getAllRestaurants(int page, int size) {
        return Collections.emptyList();
    }
    
    @Override
    public List<RestaurantAuditLog> getRestaurantAuditLogs(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.emptyList();
    }
}