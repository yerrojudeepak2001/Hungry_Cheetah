package com.foodapp.analytics.client.fallback;

import com.foodapp.analytics.client.PaymentClient;
import com.foodapp.analytics.dto.PaymentAnalytics;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;

@Component
public class PaymentClientFallback implements PaymentClient {

    @Override
    public PaymentAnalytics getTransactionAnalytics(LocalDateTime startDate, LocalDateTime endDate) {
        // Fallback implementation
        PaymentAnalytics fallback = new PaymentAnalytics();
        fallback.setStatus("FALLBACK");
        return fallback;
    }

    @Override
    public Map<String, Integer> getPaymentMethodDistribution() {
        // Fallback implementation
        return Map.of("FALLBACK", 0);
    }

    @Override
    public Map<String, Object> getRevenueAnalytics(String timeframe) {
        // Fallback implementation
        return Map.of(
                "status", "fallback",
                "timeframe", timeframe,
                "message", "Payment service unavailable");
    }
}