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
    name = "USER-SERVICE", 
    url = "${services.user-service.url:http://localhost:8083}",
    fallback = UserClientFallback.class
)
public interface UserClient {
    
    // Statistics endpoints
    @GetMapping("/api/v1/admin/users/stats")
    ResponseEntity<Map<String, Long>> getUserStats();
    
    // User management endpoints
    @GetMapping("/api/v1/admin/users")
    ResponseEntity<Page<UserAdminInfo>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String search,
            @RequestParam(required = false) String status);

    @GetMapping("/api/v1/admin/users/{userId}")
    ResponseEntity<UserAdminInfo> getUserById(@PathVariable String userId);

    @PutMapping("/api/v1/admin/users/{userId}/status")
    ResponseEntity<Void> updateUserStatus(
            @PathVariable String userId,
            @RequestParam String status,
            @RequestParam(required = false) String reason);

    @PostMapping("/api/v1/admin/users/{userId}/suspend")
    ResponseEntity<Void> suspendUser(
            @PathVariable String userId,
            @RequestBody SuspendUserRequest request);

    @PostMapping("/api/v1/admin/users/{userId}/reactivate")
    ResponseEntity<Void> reactivateUser(@PathVariable String userId);

    @GetMapping("/api/v1/admin/users/{userId}/audit-logs")
    ResponseEntity<List<UserAuditLog>> getUserAuditLogs(
            @PathVariable String userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size);

    @PostMapping("/api/v1/admin/users/bulk-action")
    ResponseEntity<Void> performBulkAction(@RequestBody BulkUserActionRequest request);

    @DeleteMapping("/api/v1/admin/users/{userId}")
    ResponseEntity<Void> deleteUser(@PathVariable String userId);

    @PostMapping("/api/v1/admin/users/cache/clear")
    ResponseEntity<Void> clearCache();
    
    // Legacy methods for backward compatibility
    @Deprecated
    @GetMapping("/api/admin/users")
    List<UserAdminInfo> getAllUsers(@RequestParam int page, @RequestParam int size);
    
    @Deprecated
    @GetMapping("/api/admin/users/audit-logs")
    List<UserAuditLog> getUserAuditLogs(@RequestParam LocalDateTime startDate, 
                                       @RequestParam LocalDateTime endDate);

    // Request DTOs
    class SuspendUserRequest {
        private String reason;
        private Integer duration;

        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
        public Integer getDuration() { return duration; }
        public void setDuration(Integer duration) { this.duration = duration; }
    }

    class BulkUserActionRequest {
        private List<String> userIds;
        private String action;
        private String reason;

        public List<String> getUserIds() { return userIds; }
        public void setUserIds(List<String> userIds) { this.userIds = userIds; }
        public String getAction() { return action; }
        public void setAction(String action) { this.action = action; }
        public String getReason() { return reason; }
        public void setReason(String reason) { this.reason = reason; }
    }
}