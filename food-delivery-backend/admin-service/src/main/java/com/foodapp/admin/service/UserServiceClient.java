package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceClient.class);
    
    public long getTotalUsers() {
        return 0L;
    }
    
    public long getActiveUsers() {
        return 0L;
    }
    
    public void clearCache() {
        logger.info("Clearing user service cache");
    }
}