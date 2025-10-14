package com.foodapp.admin.service;

import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.foodapp.admin.client.PaymentClient;

@Service
public class PaymentServiceClient {
    private static final Logger logger = LoggerFactory.getLogger(PaymentServiceClient.class);
    
    @Autowired
    private PaymentClient paymentClient;
    
    public long getPendingPayments() {
        try {
            return paymentClient.getPendingPayments();
        } catch (Exception e) {
            logger.error("Error fetching pending payments: {}", e.getMessage());
            return 0L;
        }
    }
    
    public double getTotalRevenue() {
        try {
            return paymentClient.getTotalRevenue();
        } catch (Exception e) {
            logger.error("Error fetching total revenue: {}", e.getMessage());
            return 0.0;
        }
    }
    
    public double getTodayRevenue() {
        try {
            return paymentClient.getTodayRevenue();
        } catch (Exception e) {
            logger.error("Error fetching today revenue: {}", e.getMessage());
            return 0.0;
        }
    }
    
    public double getMonthlyRevenue() {
        try {
            return paymentClient.getMonthlyRevenue();
        } catch (Exception e) {
            logger.error("Error fetching monthly revenue: {}", e.getMessage());
            return 0.0;
        }
    }
    
    public long getFailedPayments() {
        try {
            return paymentClient.getFailedPayments();
        } catch (Exception e) {
            logger.error("Error fetching failed payments: {}", e.getMessage());
            return 0L;
        }
    }
    
    public java.util.List<com.foodapp.admin.dto.PaymentAdminInfo> getAllPayments(int page, int size) {
        try {
            return paymentClient.getAllPayments(page, size);
        } catch (Exception e) {
            logger.error("Error fetching all payments: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    
    public com.foodapp.admin.dto.PaymentAdminInfo getPaymentById(String paymentId) {
        try {
            return paymentClient.getPaymentById(paymentId);
        } catch (Exception e) {
            logger.error("Error fetching payment by ID {}: {}", paymentId, e.getMessage());
            return new com.foodapp.admin.dto.PaymentAdminInfo();
        }
    }
    
    public void processRefund(String paymentId, double amount, String reason) {
        try {
            paymentClient.processRefund(paymentId, amount, reason);
        } catch (Exception e) {
            logger.error("Error processing refund for {}: {}", paymentId, e.getMessage());
        }
    }
    
    public void updatePaymentStatus(String paymentId, String status) {
        try {
            paymentClient.updatePaymentStatus(paymentId, status);
        } catch (Exception e) {
            logger.error("Error updating payment status for {}: {}", paymentId, e.getMessage());
        }
    }
    
    public java.util.List<com.foodapp.admin.dto.PaymentAdminInfo> getFailedPaymentsList(int page, int size) {
        try {
            return paymentClient.getFailedPaymentsList(page, size);
        } catch (Exception e) {
            logger.error("Error fetching failed payments list: {}", e.getMessage());
            return java.util.Collections.emptyList();
        }
    }
    
    public void retryPayment(String paymentId) {
        try {
            paymentClient.retryPayment(paymentId);
        } catch (Exception e) {
            logger.error("Error retrying payment for {}: {}", paymentId, e.getMessage());
        }
    }
    
    public void clearCache() {
        try {
            logger.info("Clearing payment service cache");
            paymentClient.clearCache();
        } catch (Exception e) {
            logger.error("Error clearing payment service cache: {}", e.getMessage());
        }
    }
}