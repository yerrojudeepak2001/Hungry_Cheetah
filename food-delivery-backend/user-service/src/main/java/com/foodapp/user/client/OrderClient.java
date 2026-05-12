package com.foodapp.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodapp.user.dto.OrderResponse;
import com.foodapp.user.dto.UserOrderStats;
import java.util.List;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/user/{userId}")
    List<OrderResponse> getUserOrders(@PathVariable("userId") String userId);
    
    @GetMapping("/api/orders/user/{userId}/active")
    List<OrderResponse> getUserActiveOrders(@PathVariable("userId") String userId);
    
    @GetMapping("/api/orders/user/{userId}/stats")
    UserOrderStats getUserOrderStats(@PathVariable("userId") String userId);
}