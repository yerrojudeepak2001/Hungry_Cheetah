package com.foodapp.order.client;

import com.foodapp.common.dto.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "payment-service")
public interface PaymentServiceClient {
    @PostMapping("/api/v1/payments")
    ApiResponse<?> processPayment(@RequestBody PaymentRequest request);
    
    @PostMapping("/api/v1/payments/refund/{orderId}")
    ApiResponse<?> processRefund(@PathVariable Long orderId);
    
    @GetMapping("/api/v1/payments/order/{orderId}")
    ApiResponse<?> getPaymentStatus(@PathVariable Long orderId);
}