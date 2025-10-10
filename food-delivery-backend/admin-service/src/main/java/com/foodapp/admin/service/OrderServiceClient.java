package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class OrderServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(OrderServiceClient.class);
    
    public long getTotalOrders() {
        // Fallback implementation
        return 0L;
    }
    
    public long getTodayOrders() {
        return 0L;
    }
    
    public long getMonthlyOrders() {
        return 0L;
    }
    
    public double getTotalRevenue() {
        return 0.0;
    }
    
    public double getAverageOrderValue() {
        return 0.0;
    }
    
    public void clearCache() {
        logger.info("Clearing order service cache");
    }
}