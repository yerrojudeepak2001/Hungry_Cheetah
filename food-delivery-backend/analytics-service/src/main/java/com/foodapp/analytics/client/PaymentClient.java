package com.foodapp.analytics.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.analytics.dto.PaymentAnalytics;
import com.foodapp.analytics.client.fallback.PaymentClientFallback;
import java.time.LocalDateTime;
import java.util.Map;

@FeignClient(name = "PAYMENT-SERVICE", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    @GetMapping("/api/payments/analytics/transactions")
    PaymentAnalytics getTransactionAnalytics(@RequestParam LocalDateTime startDate,
            @RequestParam LocalDateTime endDate);

    @GetMapping("/api/payments/analytics/methods")
    Map<String, Integer> getPaymentMethodDistribution();

    @GetMapping("/api/payments/analytics/revenue")
    Map<String, Object> getRevenueAnalytics(@RequestParam String timeframe);
}