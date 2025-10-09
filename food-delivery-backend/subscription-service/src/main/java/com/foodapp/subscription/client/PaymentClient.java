package com.foodapp.subscription.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.subscription.dto.PaymentProcessRequest;

@FeignClient(name = "PAYMENT-SERVICE", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    @PostMapping("/api/payments/subscription")
    PaymentResponse processSubscriptionPayment(@RequestBody PaymentProcessRequest request);
    
    @PostMapping("/api/payments/subscription/{subscriptionId}/cancel")
    void cancelSubscriptionPayments(@PathVariable("subscriptionId") String subscriptionId);
    
    @GetMapping("/api/payments/subscription/{subscriptionId}/history")
    List<PaymentHistory> getSubscriptionPaymentHistory(@PathVariable("subscriptionId") String subscriptionId);
}