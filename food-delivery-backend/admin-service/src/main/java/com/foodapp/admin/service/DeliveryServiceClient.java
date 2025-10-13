package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class DeliveryServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(DeliveryServiceClient.class);
    
    public long getActiveDeliveries() {
        return 0L;
    }
    
    public void clearCache() {
        logger.info("Clearing delivery service cache");
    }
}