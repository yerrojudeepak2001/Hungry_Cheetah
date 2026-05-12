package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.foodapp.admin.client.DeliveryClient;

@Service
public class DeliveryServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceClient.class);
    
    @Autowired
    private DeliveryClient deliveryClient;
    
    public long getActiveDeliveries() {
        try {
            return deliveryClient.getActiveDeliveries();
        } catch (Exception e) {
            logger.error("Error fetching active deliveries: {}", e.getMessage());
            return 0L;
        }
    }
    
    public long getCompletedDeliveries() {
        try {
            return deliveryClient.getCompletedDeliveries();
        } catch (Exception e) {
            logger.error("Error fetching completed deliveries: {}", e.getMessage());
            return 0L;
        }
    }
    
    public long getTodayDeliveries() {
        try {
            return deliveryClient.getTodayDeliveries();
        } catch (Exception e) {
            logger.error("Error fetching today deliveries: {}", e.getMessage());
            return 0L;
        }
    }
    
    public java.util.List<com.foodapp.admin.dto.DeliveryAdminInfo> getAllDeliveries(int page, int size) {
        try {
            return deliveryClient.getAllDeliveries(page, size);
        } catch (Exception e) {
            logger.error("Error fetching all deliveries: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    
    public com.foodapp.admin.dto.DeliveryAdminInfo getDeliveryById(String deliveryId) {
        try {
            return deliveryClient.getDeliveryById(deliveryId);
        } catch (Exception e) {
            logger.error("Error fetching delivery by ID {}: {}", deliveryId, e.getMessage());
            return new com.foodapp.admin.dto.DeliveryAdminInfo();
        }
    }
    
    public void updateDeliveryStatus(String deliveryId, String status) {
        try {
            deliveryClient.updateDeliveryStatus(deliveryId, status);
        } catch (Exception e) {
            logger.error("Error updating delivery status for {}: {}", deliveryId, e.getMessage());
        }
    }
    
    public java.util.List<com.foodapp.admin.dto.DriverAdminInfo> getAllDrivers(int page, int size) {
        try {
            return deliveryClient.getAllDrivers(page, size);
        } catch (Exception e) {
            logger.error("Error fetching all drivers: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    
    public void updateDriverStatus(String driverId, String status) {
        try {
            deliveryClient.updateDriverStatus(driverId, status);
        } catch (Exception e) {
            logger.error("Error updating driver status for {}: {}", driverId, e.getMessage());
        }
    }
    
    public void clearCache() {
        try {
            logger.info("Clearing delivery service cache");
            deliveryClient.clearCache();
        } catch (Exception e) {
            logger.error("Error clearing delivery service cache: {}", e.getMessage());
        }
    }
}