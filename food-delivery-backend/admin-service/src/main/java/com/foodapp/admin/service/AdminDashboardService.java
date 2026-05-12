package com.foodapp.admin.service;

import com.foodapp.admin.dto.DashboardStats;
import com.foodapp.admin.dto.SystemHealthDTO;
import com.foodapp.admin.dto.AuditLogDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
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
    
    public DashboardStats getDashboardStats() {
        DashboardStats stats = new DashboardStats();
        
        // Aggregate data from different services
        stats.setTotalUsers(userServiceClient.getTotalUsers());
        stats.setTotalOrders(orderServiceClient.getTotalOrders());
        stats.setTotalRestaurants(restaurantServiceClient.getTotalRestaurants());
        stats.setActiveDeliveries(deliveryServiceClient.getActiveDeliveries());
        stats.setTotalRevenue(paymentServiceClient.getTotalRevenue());
        
        return stats;
    }
    
    public SystemHealthDTO getSystemHealth() {
        return monitoringService.getSystemHealth();
    }
    
    public void clearAllCaches() {
        // Clear caches in all services
        orderServiceClient.clearCache();
        userServiceClient.clearCache();
        restaurantServiceClient.clearCache();
        deliveryServiceClient.clearCache();
    }
    
    public List<AuditLogDTO> getAuditLogs(String service, String action, 
                                         String startDate, String endDate) {
        return analyticsServiceClient.getAuditLogs(service, action, startDate, endDate);
    }
}