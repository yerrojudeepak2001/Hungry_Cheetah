package com.foodapp.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.foodapp.payment.dto.OrderDetails;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    
    @GetMapping("/api/orders/{orderId}")
    OrderDetails getOrderDetails(@PathVariable("orderId") Long orderId);
    
    @PutMapping("/api/orders/{orderId}/payment-status")
    void updatePaymentStatus(@PathVariable("orderId") Long orderId, String status);
}