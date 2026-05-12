package com.foodapp.admin.service;

import com.foodapp.admin.client.RestaurantClient;
import com.foodapp.admin.dto.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

@Service
public class RestaurantServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceClient.class);
    
    @Autowired
    private RestaurantClient restaurantClient;
    
    // Basic stats methods
    public long getTotalRestaurants() {
        try {
            ResponseEntity<Map<String, Long>> response = restaurantClient.getRestaurantStats();
            if (response.getBody() != null) {
                return response.getBody().getOrDefault("totalRestaurants", 0L);
            }
        } catch (Exception e) {
            logger.error("Error fetching total restaurants count", e);
        }
        return 0L;
    }
    
    public long getActiveRestaurants() {
        try {
            ResponseEntity<Map<String, Long>> response = restaurantClient.getRestaurantStats();
            if (response.getBody() != null) {
                return response.getBody().getOrDefault("activeRestaurants", 0L);
            }
        } catch (Exception e) {
            logger.error("Error fetching active restaurants count", e);
        }
        return 0L;
    }
    
    public long getPendingApprovals() {
        try {
            ResponseEntity<Map<String, Long>> response = restaurantClient.getRestaurantStats();
            if (response.getBody() != null) {
                return response.getBody().getOrDefault("pendingApprovals", 0L);
            }
        } catch (Exception e) {
            logger.error("Error fetching pending approvals count", e);
        }
        return 0L;
    }
    
    public long getSuspendedRestaurants() {
        try {
            ResponseEntity<Map<String, Long>> response = restaurantClient.getRestaurantStats();
            if (response.getBody() != null) {
                return response.getBody().getOrDefault("suspendedRestaurants", 0L);
            }
        } catch (Exception e) {
            logger.error("Error fetching suspended restaurants count", e);
        }
        return 0L;
    }
    
    // Restaurant management methods
    public Page<RestaurantAdminInfo> getAllRestaurants(int page, int size, String sortBy, String sortDirection, String status, String search) {
        try {
            logger.info("Fetching restaurants with page: {}, size: {}, status: {}", page, size, status);
            ResponseEntity<Page<RestaurantAdminInfo>> response = restaurantClient.getAllRestaurants(page, size, sortBy, sortDirection, status, search);
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error fetching restaurants", e);
            throw new RuntimeException("Failed to fetch restaurants", e);
        }
    }
    
    public RestaurantAdminInfo getRestaurantById(String restaurantId) {
        try {
            logger.info("Fetching restaurant by ID: {}", restaurantId);
            ResponseEntity<RestaurantAdminInfo> response = restaurantClient.getRestaurantById(restaurantId);
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error fetching restaurant by ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to fetch restaurant", e);
        }
    }
    
    public void updateRestaurantStatus(String restaurantId, String status, String reason) {
        try {
            logger.info("Updating restaurant {} status to {} with reason: {}", restaurantId, status, reason);
            restaurantClient.updateRestaurantStatus(restaurantId, status, reason);
        } catch (Exception e) {
            logger.error("Error updating restaurant status for ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to update restaurant status", e);
        }
    }
    
    public void approveRestaurant(String restaurantId, String comments) {
        try {
            logger.info("Approving restaurant {} with comments: {}", restaurantId, comments);
            RestaurantClient.ApproveRestaurantRequest request = new RestaurantClient.ApproveRestaurantRequest();
            request.setComments(comments);
            restaurantClient.approveRestaurant(restaurantId, request);
        } catch (Exception e) {
            logger.error("Error approving restaurant ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to approve restaurant", e);
        }
    }
    
    public void rejectRestaurant(String restaurantId, String reason) {
        try {
            logger.info("Rejecting restaurant {} with reason: {}", restaurantId, reason);
            RestaurantClient.RejectRestaurantRequest request = new RestaurantClient.RejectRestaurantRequest();
            request.setReason(reason);
            restaurantClient.rejectRestaurant(restaurantId, request);
        } catch (Exception e) {
            logger.error("Error rejecting restaurant ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to reject restaurant", e);
        }
    }
    
    public void suspendRestaurant(String restaurantId, String reason, Integer duration) {
        try {
            logger.info("Suspending restaurant {} for {} days with reason: {}", restaurantId, duration, reason);
            RestaurantClient.SuspendRestaurantRequest request = new RestaurantClient.SuspendRestaurantRequest();
            request.setReason(reason);
            request.setDuration(duration);
            restaurantClient.suspendRestaurant(restaurantId, request);
        } catch (Exception e) {
            logger.error("Error suspending restaurant ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to suspend restaurant", e);
        }
    }
    
    public void reactivateRestaurant(String restaurantId) {
        try {
            logger.info("Reactivating restaurant: {}", restaurantId);
            restaurantClient.reactivateRestaurant(restaurantId);
        } catch (Exception e) {
            logger.error("Error reactivating restaurant ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to reactivate restaurant", e);
        }
    }
    
    public List<RestaurantAuditLog> getRestaurantAuditLogs(String restaurantId, int page, int size) {
        try {
            logger.info("Fetching audit logs for restaurant: {}", restaurantId);
            ResponseEntity<List<RestaurantAuditLog>> response = restaurantClient.getRestaurantAuditLogs(restaurantId, page, size);
            return response.getBody();
        } catch (Exception e) {
            logger.error("Error fetching audit logs for restaurant ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to fetch audit logs", e);
        }
    }
    
    public void deleteRestaurant(String restaurantId) {
        try {
            logger.info("Deleting restaurant: {}", restaurantId);
            restaurantClient.deleteRestaurant(restaurantId);
        } catch (Exception e) {
            logger.error("Error deleting restaurant ID: {}", restaurantId, e);
            throw new RuntimeException("Failed to delete restaurant", e);
        }
    }
    
    public void clearCache() {
        try {
            logger.info("Clearing restaurant service cache");
            restaurantClient.clearCache();
        } catch (Exception e) {
            logger.error("Error clearing restaurant cache", e);
            throw new RuntimeException("Failed to clear cache", e);
        }
    }
}