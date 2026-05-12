package com.foodapp.admin.client;

import org.springframework.stereotype.Component;
import com.foodapp.admin.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;

@Component
public class DeliveryClientFallback implements DeliveryClient {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryClientFallback.class);

    @Override
    public long getActiveDeliveries() {
        logger.warn("Fallback: getActiveDeliveries - Delivery service unavailable");
        return 0L;
    }

    @Override
    public long getCompletedDeliveries() {
        logger.warn("Fallback: getCompletedDeliveries - Delivery service unavailable");
        return 0L;
    }

    @Override
    public long getTodayDeliveries() {
        logger.warn("Fallback: getTodayDeliveries - Delivery service unavailable");
        return 0L;
    }

    @Override
    public List<DeliveryAdminInfo> getAllDeliveries(int page, int size) {
        logger.warn("Fallback: getAllDeliveries - Delivery service unavailable");
        return Collections.emptyList();
    }

    @Override
    public DeliveryAdminInfo getDeliveryById(String deliveryId) {
        logger.warn("Fallback: getDeliveryById - Delivery service unavailable for deliveryId: {}", deliveryId);
        return new DeliveryAdminInfo();
    }

    @Override
    public void updateDeliveryStatus(String deliveryId, String status) {
        logger.warn("Fallback: updateDeliveryStatus - Delivery service unavailable for deliveryId: {}", deliveryId);
    }

    @Override
    public List<DriverAdminInfo> getAllDrivers(int page, int size) {
        logger.warn("Fallback: getAllDrivers - Delivery service unavailable");
        return Collections.emptyList();
    }

    @Override
    public void updateDriverStatus(String driverId, String status) {
        logger.warn("Fallback: updateDriverStatus - Delivery service unavailable for driverId: {}", driverId);
    }

    @Override
    public void clearCache() {
        logger.warn("Fallback: clearCache - Delivery service unavailable");
    }
}