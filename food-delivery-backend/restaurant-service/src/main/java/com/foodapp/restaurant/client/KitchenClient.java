package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.foodapp.restaurant.dto.kitchen.KitchenOrder;
import com.foodapp.restaurant.dto.kitchen.KitchenStatus;
import com.foodapp.restaurant.dto.kitchen.KitchenStatusUpdate;

@FeignClient(name = "KITCHEN-SERVICE", fallback = com.foodapp.restaurant.client.fallback.KitchenClientFallback.class)
public interface KitchenClient {
    @PostMapping("/api/kitchen/orders")
    void sendToKitchen(@RequestBody KitchenOrder order);
    
    @GetMapping("/api/kitchen/orders/{orderId}/status")
    KitchenStatus getOrderStatus(@PathVariable("orderId") String orderId);
    
    @GetMapping("/api/kitchen/capacity")
    Map<String, Object> getCurrentCapacity(@RequestParam String restaurantId);
    
    @PutMapping("/api/kitchen/orders/{orderId}/status")
    void updateOrderStatus(@PathVariable("orderId") String orderId,
                         @RequestBody KitchenStatusUpdate update);
}