package com.foodapp.admin.controller;

import com.foodapp.admin.dto.ApiResponse;
import com.foodapp.admin.dto.SystemHealthDTO;
import com.foodapp.admin.service.MonitoringService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin/system")
@RequiredArgsConstructor
//@CrossOrigin(origins = "*")
public class SystemMonitoringController {

    private final MonitoringService monitoringService;

    @GetMapping("/health")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SystemHealthDTO> getSystemHealth() {
        SystemHealthDTO health = monitoringService.getSystemHealth();
        return new ApiResponse<>(true, "System health retrieved successfully", health);
    }

    @PostMapping("/cache/clear-all")
    @PreAuthorize("hasRole('SUPER_ADMIN')")
    public ApiResponse<Void> clearAllCaches() {
        monitoringService.clearAllCaches();
        return new ApiResponse<>(true, "All system caches cleared successfully", null);
    }

    @GetMapping("/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<SystemStatus> getSystemStatus() {
        SystemStatus status = SystemStatus.builder()
            .uptime("15 days, 3 hours")
            .version("1.0.0")
            .environment("production")
            .build();
        
        return new ApiResponse<>(true, "System status retrieved successfully", status);
    }

    // Inner classes for response DTOs
    @lombok.Data
    @lombok.Builder
    public static class SystemStatus {
        private String uptime;
        private String version;
        private String environment;
    }
}