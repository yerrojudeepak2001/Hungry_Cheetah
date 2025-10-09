package com.foodapp.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.cart.dto.OrderRequest;
import com.foodapp.cart.dto.OrderResponse;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @PostMapping("/api/orders")
    OrderResponse createOrder(@RequestBody OrderRequest request);
    
    @GetMapping("/api/orders/validate-items")
    boolean validateOrderItems(@RequestBody List<String> itemIds);
    
    @GetMapping("/api/orders/estimated-time")
    int getEstimatedPreparationTime(@RequestBody List<String> itemIds);
}