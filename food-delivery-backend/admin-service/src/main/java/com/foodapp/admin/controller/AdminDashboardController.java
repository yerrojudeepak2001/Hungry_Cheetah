package com.foodapp.admin.controller;

import com.foodapp.admin.dto.ApiResponse;
import com.foodapp.admin.service.AdminDashboardService;
import com.foodapp.admin.dto.DashboardStats;
import com.foodapp.admin.dto.SystemHealthDTO;
import com.foodapp.admin.dto.AuditLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/dashboard")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class AdminDashboardController {

    private final AdminDashboardService dashboardService;
    
    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DashboardStats> getDashboardStats() {
        return new ApiResponse<>(true, "Dashboard stats retrieved successfully", 
            dashboardService.getDashboardStats());
    }
    
    @GetMapping("/health")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SystemHealthDTO> getSystemHealth() {
        return new ApiResponse<>(true, "System health retrieved successfully", 
            dashboardService.getSystemHealth());
    }
    
    @PostMapping("/cache/clear")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<Void> clearSystemCache() {
        dashboardService.clearAllCaches();
        return new ApiResponse<>(true, "System cache cleared successfully", null);
    }
    
    @GetMapping("/audit-logs")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<AuditLogDTO>> getAuditLogs(
            @RequestParam(required = false) String service,
            @RequestParam(required = false) String action,
            @RequestParam(required = false) String startDate,
            @RequestParam(required = false) String endDate) {
        return new ApiResponse<>(true, "Audit logs retrieved successfully", 
            dashboardService.getAuditLogs(service, action, startDate, endDate));
    }

    @GetMapping("/overview")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<DashboardOverview> getDashboardOverview() {
        DashboardOverview overview = DashboardOverview.builder()
            .stats(dashboardService.getDashboardStats())
            .health(dashboardService.getSystemHealth())
            .build();
        
        return new ApiResponse<>(true, "Dashboard overview retrieved successfully", overview);
    }

    // Inner classes for response DTOs
    @lombok.Data
    @lombok.Builder
    public static class DashboardOverview {
        private DashboardStats stats;
        private SystemHealthDTO health;
    }
}