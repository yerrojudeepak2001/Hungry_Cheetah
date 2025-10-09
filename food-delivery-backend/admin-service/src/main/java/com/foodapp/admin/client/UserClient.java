package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.UserAdminInfo;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/admin/users")
    List<UserAdminInfo> getAllUsers(@RequestParam int page, @RequestParam int size);
    
    @PutMapping("/api/admin/users/{userId}/status")
    void updateUserStatus(@PathVariable("userId") String userId, @RequestParam String status);
    
    @GetMapping("/api/admin/users/audit-logs")
    List<UserAuditLog> getUserAuditLogs(@RequestParam LocalDateTime startDate, 
                                       @RequestParam LocalDateTime endDate);
}