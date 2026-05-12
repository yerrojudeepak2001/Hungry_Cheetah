package com.foodapp.user.client;

import com.foodapp.user.dto.PaymentMethodDto;
import com.foodapp.user.dto.PaymentHistoryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;

@Component
public class PaymentClientFallback implements PaymentClient {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentClientFallback.class);
    
    @Override
    public List<PaymentMethodDto> getUserPaymentMethods(String userId) {
        logger.error("Payment service is unavailable. Returning empty payment methods for user: {}", userId);
        return Collections.emptyList();
    }
    
    @Override
    public PaymentMethodDto addPaymentMethod(String userId, PaymentMethodDto paymentMethod) {
        logger.error("Payment service is unavailable. Cannot add payment method for user: {}", userId);
        return null;
    }
    
    @Override
    public void removePaymentMethod(String userId, String methodId) {
        logger.error("Payment service is unavailable. Cannot remove payment method {} for user: {}", methodId, userId);
    }
    
    @Override
    public List<PaymentHistoryDto> getPaymentHistory(String userId) {
        logger.error("Payment service is unavailable. Returning empty payment history for user: {}", userId);
        return Collections.emptyList();
    }
    
    @Override
    public void setDefaultPaymentMethod(String userId, String methodId) {
        logger.error("Payment service is unavailable. Cannot set default payment method {} for user: {}", methodId, userId);
    }
    
    @Override
    public Double getWalletBalance(String userId) {
        logger.error("Payment service is unavailable. Returning 0 wallet balance for user: {}", userId);
        return 0.0;
    }
    
    @Override
    public void addFundsToWallet(String userId, Double amount) {
        logger.error("Payment service is unavailable. Cannot add funds {} to wallet for user: {}", amount, userId);
    }
}