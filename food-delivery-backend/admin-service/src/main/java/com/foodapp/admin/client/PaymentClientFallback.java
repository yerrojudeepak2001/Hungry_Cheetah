package com.foodapp.admin.client;

import org.springframework.stereotype.Component;
import com.foodapp.admin.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;

@Component
public class PaymentClientFallback implements PaymentClient {
    private static final Logger logger = LoggerFactory.getLogger(PaymentClientFallback.class);

    @Override
    public long getPendingPayments() {
        logger.warn("Fallback: getPendingPayments - Payment service unavailable");
        return 0L;
    }

    @Override
    public double getTotalRevenue() {
        logger.warn("Fallback: getTotalRevenue - Payment service unavailable");
        return 0.0;
    }

    @Override
    public double getTodayRevenue() {
        logger.warn("Fallback: getTodayRevenue - Payment service unavailable");
        return 0.0;
    }

    @Override
    public double getMonthlyRevenue() {
        logger.warn("Fallback: getMonthlyRevenue - Payment service unavailable");
        return 0.0;
    }

    @Override
    public long getFailedPayments() {
        logger.warn("Fallback: getFailedPayments - Payment service unavailable");
        return 0L;
    }

    @Override
    public List<PaymentAdminInfo> getAllPayments(int page, int size) {
        logger.warn("Fallback: getAllPayments - Payment service unavailable");
        return Collections.emptyList();
    }

    @Override
    public PaymentAdminInfo getPaymentById(String paymentId) {
        logger.warn("Fallback: getPaymentById - Payment service unavailable for paymentId: {}", paymentId);
        return new PaymentAdminInfo();
    }

    @Override
    public void processRefund(String paymentId, double amount, String reason) {
        logger.warn("Fallback: processRefund - Payment service unavailable for paymentId: {}", paymentId);
    }

    @Override
    public void updatePaymentStatus(String paymentId, String status) {
        logger.warn("Fallback: updatePaymentStatus - Payment service unavailable for paymentId: {}", paymentId);
    }

    @Override
    public List<PaymentAdminInfo> getFailedPaymentsList(int page, int size) {
        logger.warn("Fallback: getFailedPaymentsList - Payment service unavailable");
        return Collections.emptyList();
    }

    @Override
    public void retryPayment(String paymentId) {
        logger.warn("Fallback: retryPayment - Payment service unavailable for paymentId: {}", paymentId);
    }

    @Override
    public void clearCache() {
        logger.warn("Fallback: clearCache - Payment service unavailable");
    }
}