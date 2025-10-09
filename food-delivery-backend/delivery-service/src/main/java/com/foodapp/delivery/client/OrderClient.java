package com.foodapp.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import com.foodapp.delivery.dto.OrderDetails;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    
    @GetMapping("/api/orders/{orderId}")
    OrderDetails getOrderDetails(@PathVariable("orderId") Long orderId);
    
    @PutMapping("/api/orders/{orderId}/delivery-status")
    void updateDeliveryStatus(@PathVariable("orderId") Long orderId, String status);
}