package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class PaymentServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceClient.class);
    
    public long getPendingPayments() {
        return 0L;
    }
    
    public double getTotalRevenue() {
        return 0.0;
    }
}