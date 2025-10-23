package com.foodapp.admin.controller;

import com.foodapp.admin.dto.ApiResponse;
import com.foodapp.admin.dto.UserAdminInfo;
import com.foodapp.admin.dto.UserAuditLog;
import com.foodapp.admin.service.UserServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/users")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class UserManagementController {

    private final UserServiceClient userServiceClient;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserStats> getUserStats() {
        UserStats stats = UserStats.builder()
            .totalUsers(userServiceClient.getTotalUsers())
            .activeUsers(userServiceClient.getActiveUsers())
            .newUsersToday(userServiceClient.getNewUsersToday())
            .newUsersThisMonth(userServiceClient.getNewUsersThisMonth())
            .build();
        
        return new ApiResponse<>(true, "User statistics retrieved successfully", stats);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<UserAdminInfo>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status) {
        
        Page<UserAdminInfo> users = userServiceClient.getAllUsers(page, size, sortBy, sortDirection, search, status);
        return new ApiResponse<>(true, "Users retrieved successfully", users);
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<UserAdminInfo> getUserById(@PathVariable String userId) {
        UserAdminInfo user = userServiceClient.getUserById(userId);
        return new ApiResponse<>(true, "User retrieved successfully", user);
    }

    @PutMapping("/{userId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateUserStatus(
            @PathVariable String userId,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {
        
        userServiceClient.updateUserStatus(userId, status, reason);
        return new ApiResponse<>(true, "User status updated successfully", null);
    }

    @PostMapping("/{userId}/suspend")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> suspendUser(
            @PathVariable String userId,
            @RequestBody SuspendUserRequest request) {
        
        userServiceClient.suspendUser(userId, request.getReason(), request.getDuration());
        return new ApiResponse<>(true, "User suspended successfully", null);
    }

    @PostMapping("/{userId}/reactivate")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> reactivateUser(@PathVariable String userId) {
        userServiceClient.reactivateUser(userId);
        return new ApiResponse<>(true, "User reactivated successfully", null);
    }

    @GetMapping("/{userId}/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<UserAuditLog>> getUserAuditLogs(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        
        List<UserAuditLog> auditLogs = userServiceClient.getUserAuditLogs(userId, page, size);
        return new ApiResponse<>(true, "User audit logs retrieved successfully", auditLogs);
    }

    @PostMapping("/bulk-action")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<Void> performBulkAction(@RequestBody BulkUserActionRequest request) {
        userServiceClient.performBulkAction(request.getUserIds(), request.getAction(), request.getReason());
        return new ApiResponse<>(true, "Bulk action performed successfully", null);
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<Void> deleteUser(@PathVariable String userId) {
        userServiceClient.deleteUser(userId);
        return new ApiResponse<>(true, "User deleted successfully", null);
    }

    // Inner classes for request/response DTOs
    @lombok.Data
    @lombok.Builder
    public static class UserStats {
        private long totalUsers;
        private long activeUsers;
        private long newUsersToday;
        private long newUsersThisMonth;
    }

    @lombok.Data
    public static class SuspendUserRequest {
        private String reason;
        private Integer duration; // days
    }

    @lombok.Data
    public static class BulkUserActionRequest {
        private List<String> userIds;
        private String action; // SUSPEND, ACTIVATE, DELETE
        private String reason;
    }
}