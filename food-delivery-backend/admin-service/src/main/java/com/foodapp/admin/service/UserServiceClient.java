package com.foodapp.admin.service;

import com.foodapp.admin.dto.UserAdminInfo;
import com.foodapp.admin.dto.UserAuditLog;
import com.foodapp.admin.client.UserClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;

@Service
public class UserServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class);
    
    @Autowired
    private UserClient userClient;
    
    // Basic stats methods
    public long getTotalUsers() {
        try {
            var response = userClient.getUserStats();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getOrDefault("totalUsers", 0L);
            }
        } catch (Exception e) {
            logger.warn("Failed to fetch total users from user service: {}", e.getMessage());
        }
        return 0L; // Fallback
    }
    
    public long getActiveUsers() {
        try {
            var response = userClient.getUserStats();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getOrDefault("activeUsers", 0L);
            }
        } catch (Exception e) {
            logger.warn("Failed to fetch active users from user service: {}", e.getMessage());
        }
        return 0L; // Fallback
    }
    
    public long getNewUsersToday() {
        try {
            var response = userClient.getUserStats();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getOrDefault("newUsersToday", 0L);
            }
        } catch (Exception e) {
            logger.warn("Failed to fetch new users today from user service: {}", e.getMessage());
        }
        return 0L; // Fallback
    }
    
    public long getNewUsersThisMonth() {
        try {
            var response = userClient.getUserStats();
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody().getOrDefault("newUsersThisMonth", 0L);
            }
        } catch (Exception e) {
            logger.warn("Failed to fetch new users this month from user service: {}", e.getMessage());
        }
        return 0L; // Fallback
    }
    
    // User management methods
    public Page<UserAdminInfo> getAllUsers(int page, int size, String sortBy, String sortDirection, String search, String status) {
        try {
            logger.info("Fetching users with page: {}, size: {}, search: {}", page, size, search);
            var response = userClient.getAllUsers(page, size, sortBy, sortDirection, search, status);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            logger.warn("Failed to fetch users from user service: {}", e.getMessage());
        }
        return new PageImpl<>(Collections.emptyList());
    }
    
    public UserAdminInfo getUserById(String userId) {
        try {
            logger.info("Fetching user by ID: {}", userId);
            var response = userClient.getUserById(userId);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            logger.warn("Failed to fetch user {} from user service: {}", userId, e.getMessage());
        }
        return new UserAdminInfo();
    }
    
    public void updateUserStatus(String userId, String status, String reason) {
        try {
            logger.info("Updating user {} status to {} with reason: {}", userId, status, reason);
            userClient.updateUserStatus(userId, status, reason);
        } catch (Exception e) {
            logger.error("Failed to update user {} status: {}", userId, e.getMessage());
        }
    }
    
    public void suspendUser(String userId, String reason, Integer duration) {
        try {
            logger.info("Suspending user {} for {} days with reason: {}", userId, duration, reason);
            UserClient.SuspendUserRequest request = new UserClient.SuspendUserRequest();
            request.setReason(reason);
            request.setDuration(duration);
            userClient.suspendUser(userId, request);
        } catch (Exception e) {
            logger.error("Failed to suspend user {}: {}", userId, e.getMessage());
        }
    }
    
    public void reactivateUser(String userId) {
        try {
            logger.info("Reactivating user: {}", userId);
            userClient.reactivateUser(userId);
        } catch (Exception e) {
            logger.error("Failed to reactivate user {}: {}", userId, e.getMessage());
        }
    }
    
    public List<UserAuditLog> getUserAuditLogs(String userId, int page, int size) {
        try {
            logger.info("Fetching audit logs for user: {}", userId);
            var response = userClient.getUserAuditLogs(userId, page, size);
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
        } catch (Exception e) {
            logger.warn("Failed to fetch audit logs for user {}: {}", userId, e.getMessage());
        }
        return Collections.emptyList();
    }
    
    public void performBulkAction(List<String> userIds, String action, String reason) {
        try {
            logger.info("Performing bulk action {} on {} users with reason: {}", action, userIds.size(), reason);
            UserClient.BulkUserActionRequest request = new UserClient.BulkUserActionRequest();
            request.setUserIds(userIds);
            request.setAction(action);
            request.setReason(reason);
            userClient.performBulkAction(request);
        } catch (Exception e) {
            logger.error("Failed to perform bulk action: {}", e.getMessage());
        }
    }
    
    public void deleteUser(String userId) {
        try {
            logger.info("Deleting user: {}", userId);
            userClient.deleteUser(userId);
        } catch (Exception e) {
            logger.error("Failed to delete user {}: {}", userId, e.getMessage());
        }
    }
    
    public void clearCache() {
        try {
            logger.info("Clearing user service cache");
            userClient.clearCache();
        } catch (Exception e) {
            logger.error("Failed to clear user service cache: {}", e.getMessage());
        }
    }
}