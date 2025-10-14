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
public class UserClientFallback implements UserClient {
    
    @Override
    public ResponseEntity<Map<String, Long>> getUserStats() {
        Map<String, Long> fallbackStats = new HashMap<>();
        fallbackStats.put("totalUsers", 0L);
        fallbackStats.put("activeUsers", 0L);
        fallbackStats.put("newUsersToday", 0L);
        fallbackStats.put("newUsersThisMonth", 0L);
        return ResponseEntity.ok(fallbackStats);
    }

    @Override
    public ResponseEntity<Page<UserAdminInfo>> getAllUsers(int page, int size, String sortBy, String sortDirection, String search, String status) {
        return ResponseEntity.ok(new PageImpl<>(Collections.emptyList()));
    }

    @Override
    public ResponseEntity<UserAdminInfo> getUserById(String userId) {
        return ResponseEntity.ok(new UserAdminInfo());
    }

    @Override
    public ResponseEntity<Void> updateUserStatus(String userId, String status, String reason) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> suspendUser(String userId, SuspendUserRequest request) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> reactivateUser(String userId) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<List<UserAuditLog>> getUserAuditLogs(String userId, int page, int size) {
        return ResponseEntity.ok(Collections.emptyList());
    }

    @Override
    public ResponseEntity<Void> performBulkAction(BulkUserActionRequest request) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> deleteUser(String userId) {
        return ResponseEntity.ok().build();
    }

    @Override
    public ResponseEntity<Void> clearCache() {
        return ResponseEntity.ok().build();
    }
    
    // Legacy methods for backward compatibility
    @Override
    public List<UserAdminInfo> getAllUsers(int page, int size) {
        return Collections.emptyList();
    }
    
    @Override
    public List<UserAuditLog> getUserAuditLogs(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.emptyList();
    }
}