package com.foodapp.admin.service;

import com.foodapp.admin.dto.DashboardStats;
import com.foodapp.admin.dto.SystemHealthDTO;
import com.foodapp.admin.client.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import java.util.*;

@Service
@RequiredArgsConstructor
public class AdminDashboardService {
    
    private final OrderServiceClient orderServiceClient;
    private final UserServiceClient userServiceClient;
    private final RestaurantServiceClient restaurantServiceClient;
    private final DeliveryServiceClient deliveryServiceClient;
    private final PaymentServiceClient paymentServiceClient;
    private final AnalyticsServiceClient analyticsServiceClient;
    private final MonitoringService monitoringService;
    
    @Cacheable(value = "dashboardStats", key = "'current'")
    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        // Aggregate data from different services
        stats.setTotalUsers(userServiceClient.getTotalUsers());
        stats.setActiveUsers(userServiceClient.getActiveUsers());
        stats.setTotalOrders(orderServiceClient.getTotalOrders());
        stats.setTotalRestaurants(restaurantServiceClient.getTotalRestaurants());
        stats.setActiveDeliveries(deliveryServiceClient.getActiveDeliveries());
        stats.setTotalRevenue(paymentServiceClient.getTotalRevenue());
        
        // Get real-time metrics
        stats.setCurrentOnlineUsers(monitoringService.getCurrentOnlineUsers());
        stats.setSystemLoad(monitoringService.getSystemLoad());
        
        return stats;
    }
    
    public SystemHealthDTO getSystemHealth() {
        SystemHealthDTO health = new SystemHealthDTO();
        
        // Check all service healths
        health.setServicesHealth(monitoringService.getAllServicesHealth());
        health.setDatabaseHealth(monitoringService.getDatabaseHealth());
        health.setCacheHealth(monitoringService.getCacheHealth());
        health.setMessageQueueHealth(monitoringService.getMessageQueueHealth());
        
        // Get system metrics
        health.setMemoryUsage(monitoringService.getMemoryUsage());
        health.setCpuUsage(monitoringService.getCpuUsage());
        health.setDiskUsage(monitoringService.getDiskUsage());
        
        return health;
    }
    
    @Scheduled(fixedRate = 300000) // Every 5 minutes
    public void updateSystemMetrics() {
        monitoringService.collectSystemMetrics();
    }
    
    public void clearAllCaches() {
        // Clear caches in all services
        orderServiceClient.clearCache();
        userServiceClient.clearCache();
        restaurantServiceClient.clearCache();
        deliveryServiceClient.clearCache();
        // Clear local caches
        cacheManager.getCacheNames()
            .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
    }
    
    public List<AuditLogDTO> getAuditLogs(String service, String action, 
                                         String startDate, String endDate) {
        return analyticsServiceClient.getAuditLogs(service, action, startDate, endDate);
    }
}