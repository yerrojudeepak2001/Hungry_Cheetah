package com.foodapp.user.client;

import com.foodapp.user.dto.DeliveryAddressDto;
import com.foodapp.user.dto.DeliveryTrackingDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class DeliveryClientFallback implements DeliveryClient {
    
    private static final Logger logger = LoggerFactory.getLogger(DeliveryClientFallback.class);
    
    @Override
    public List<DeliveryTrackingDto> getActiveDeliveries(String userId) {
        logger.error("Delivery service is unavailable. Returning empty active deliveries for user: {}", userId);
        return Collections.emptyList();
    }
    
    @Override
    public List<DeliveryTrackingDto> getDeliveryHistory(String userId) {
        logger.error("Delivery service is unavailable. Returning empty delivery history for user: {}", userId);
        return Collections.emptyList();
    }
    
    @Override
    public DeliveryTrackingDto trackDelivery(String deliveryId) {
        logger.error("Delivery service is unavailable. Cannot track delivery: {}", deliveryId);
        return null;
    }
    
    @Override
    public Boolean validateDeliveryAddress(String userId, DeliveryAddressDto address) {
        logger.error("Delivery service is unavailable. Cannot validate address for user: {}", userId);
        return false; // Conservative approach - assume invalid if service is down
    }
    
    @Override
    public Integer getDeliveryTimeEstimate(String userId, String restaurantId, String addressId) {
        logger.error("Delivery service is unavailable. Returning default estimate for user: {}", userId);
        return 60; // Default 60 minutes
    }
    
    @Override
    public void rateDelivery(String userId, String deliveryId, Integer rating, String feedback) {
        logger.error("Delivery service is unavailable. Cannot rate delivery {} for user: {}", deliveryId, userId);
    }
}