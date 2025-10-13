package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class RestaurantServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(RestaurantServiceClient.class);
    
    public long getTotalRestaurants() {
        return 0L;
    }
    
    public void clearCache() {
        logger.info("Clearing restaurant service cache");
    }
}