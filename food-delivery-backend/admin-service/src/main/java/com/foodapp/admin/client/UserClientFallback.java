package com.foodapp.admin.client;

import com.foodapp.admin.dto.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Collections;

@Component
public class UserClientFallback implements UserClient {
    
    @Override
    public List<UserAdminInfo> getAllUsers(int page, int size) {
        return Collections.emptyList();
    }
    
    @Override
    public void updateUserStatus(String userId, String status) {
        // Fallback - do nothing
    }
    
    @Override
    public List<UserAuditLog> getUserAuditLogs(LocalDateTime startDate, LocalDateTime endDate) {
        return Collections.emptyList();
    }
}