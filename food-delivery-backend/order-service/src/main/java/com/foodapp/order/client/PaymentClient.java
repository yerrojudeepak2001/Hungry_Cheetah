package com.foodapp.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.foodapp.order.dto.PaymentRequest;
import com.foodapp.order.dto.PaymentResponse;

@FeignClient(name = "PAYMENT-SERVICE")
public interface PaymentClient {
    
    @PostMapping("/api/payments/process")
    PaymentResponse processPayment(@RequestBody PaymentRequest paymentRequest);
    
    @GetMapping("/api/payments/{orderId}")
    PaymentResponse getPaymentStatus(@PathVariable("orderId") Long orderId);
}