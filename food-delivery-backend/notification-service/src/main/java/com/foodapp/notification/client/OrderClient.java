package com.foodapp.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.notification.dto.OrderDetails;
import com.foodapp.notification.dto.OrderStatusChange;
import java.util.List;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/{orderId}")
    OrderDetails getOrderDetails(@PathVariable("orderId") String orderId);
    
    @GetMapping("/api/orders/{orderId}/status-history")
    List<OrderStatusChange> getOrderStatusHistory(@PathVariable("orderId") String orderId);
}