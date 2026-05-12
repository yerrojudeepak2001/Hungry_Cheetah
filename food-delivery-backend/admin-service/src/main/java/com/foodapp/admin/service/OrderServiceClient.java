package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.foodapp.admin.client.OrderClient;

@Service
public class OrderServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceClient.class);
    
    @Autowired
    private OrderClient orderClient;
    
    public long getTotalOrders() {
        try {
            return orderClient.getTotalOrders();
        } catch (Exception e) {
            logger.error("Error fetching total orders: {}", e.getMessage());
            return 0L;
        }
    }
    
    public long getTodayOrders() {
        try {
            return orderClient.getTodayOrders();
        } catch (Exception e) {
            logger.error("Error fetching today orders: {}", e.getMessage());
            return 0L;
        }
    }
    
    public long getMonthlyOrders() {
        try {
            return orderClient.getMonthlyOrders();
        } catch (Exception e) {
            logger.error("Error fetching monthly orders: {}", e.getMessage());
            return 0L;
        }
    }
    
    public double getTotalRevenue() {
        try {
            return orderClient.getTotalRevenue();
        } catch (Exception e) {
            logger.error("Error fetching total revenue: {}", e.getMessage());
            return 0.0;
        }
    }
    
    public double getAverageOrderValue() {
        try {
            return orderClient.getAverageOrderValue();
        } catch (Exception e) {
            logger.error("Error fetching average order value: {}", e.getMessage());
            return 0.0;
        }
    }
    
    // Enhanced order management methods
    public org.springframework.data.domain.Page<com.foodapp.admin.dto.OrderAdminInfo> getAllOrders(
            int page, int size, String sortBy, String sortDirection, String status, String search) {
        try {
            logger.info("Fetching orders with page: {}, size: {}, status: {}", page, size, status);
            return orderClient.getAllOrders(page, size, sortBy, sortDirection, status, search);
        } catch (Exception e) {
            logger.error("Error fetching orders: {}", e.getMessage());
            return new org.springframework.data.domain.PageImpl<>(java.util.Collections.emptyList());
        }
    }
    
    public com.foodapp.admin.dto.OrderAdminInfo getOrderById(String orderId) {
        try {
            logger.info("Fetching order by ID: {}", orderId);
            return orderClient.getOrderById(orderId);
        } catch (Exception e) {
            logger.error("Error fetching order by ID {}: {}", orderId, e.getMessage());
            return new com.foodapp.admin.dto.OrderAdminInfo();
        }
    }
    
    public void updateOrderStatus(String orderId, String status, String reason) {
        try {
            logger.info("Updating order {} status to {} with reason: {}", orderId, status, reason);
            orderClient.updateOrderStatus(orderId, status, reason);
        } catch (Exception e) {
            logger.error("Error updating order status for {}: {}", orderId, e.getMessage());
        }
    }
    
    public void cancelOrder(String orderId, String reason) {
        try {
            logger.info("Cancelling order {} with reason: {}", orderId, reason);
            orderClient.cancelOrder(orderId, reason);
        } catch (Exception e) {
            logger.error("Error cancelling order {}: {}", orderId, e.getMessage());
        }
    }
    
    public void refundOrder(String orderId, double amount, String reason) {
        try {
            logger.info("Refunding order {} amount: {} with reason: {}", orderId, amount, reason);
            orderClient.refundOrder(orderId, amount, reason);
        } catch (Exception e) {
            logger.error("Error refunding order {}: {}", orderId, e.getMessage());
        }
    }
    
    public java.util.List<com.foodapp.admin.dto.OrderStatusHistory> getOrderHistory(String orderId) {
        try {
            logger.info("Fetching order history for: {}", orderId);
            return orderClient.getOrderHistory(orderId);
        } catch (Exception e) {
            logger.error("Error fetching order history for {}: {}", orderId, e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    
    public void clearCache() {
        try {
            logger.info("Clearing order service cache");
            orderClient.clearCache();
        } catch (Exception e) {
            logger.error("Error clearing order service cache: {}", e.getMessage());
        }
    }
}