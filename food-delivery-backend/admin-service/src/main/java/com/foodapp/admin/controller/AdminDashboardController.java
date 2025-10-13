package com.foodapp.admin.controller;

import com.foodapp.admin.dto.ApiResponse;
import com.foodapp.admin.service.AdminDashboardService;
import com.foodapp.admin.dto.DashboardStats;
import com.foodapp.admin.dto.SystemHealthDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@RequiredArgsConstructor
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;
    
    @GetMapping("/dashboard/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DashboardStats> getDashboardStats() {
        return new ApiResponse<>(true, "Dashboard stats retrieved", 
            dashboardService.getDashboardStats());
    }
    
    @GetMapping("/system/health")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SystemHealthDTO> getSystemHealth() {
        return new ApiResponse<>(true, "System health retrieved", 
            dashboardService.getSystemHealth());
    }
    
    @PostMapping("/system/cache/clear")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<Void> clearSystemCache() {
        dashboardService.clearAllCaches();
        return new ApiResponse<>(true, "System cache cleared", null);
    }
    
    @GetMapping("/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<?> getAuditLogs(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return new ApiResponse<>(true, "Audit logs retrieved", 
            dashboardService.getAuditLogs(service, action, startDate, endDate));
    }
}