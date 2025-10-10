package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import com.foodapp.restaurant.dto.OrderResponse;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    
    @GetMapping("/api/orders/restaurant/{restaurantId}")
    List<OrderResponse> getRestaurantOrders(@PathVariable("restaurantId") Long restaurantId);
    
    @GetMapping("/api/orders/{orderId}")
    OrderResponse getOrderDetails(@PathVariable("orderId") Long orderId);
}