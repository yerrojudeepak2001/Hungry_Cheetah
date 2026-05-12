package com.foodapp.order.client;

import com.foodapp.order.dto.ApiResponse;
import com.foodapp.order.dto.OrderDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "restaurant-service")
public interface RestaurantServiceClient {
    @GetMapping("/api/v1/restaurants/{restaurantId}")
    ApiResponse<?> getRestaurant(@PathVariable Long restaurantId);

    @GetMapping("/api/v1/restaurants/{restaurantId}/menu-items/{menuItemId}")
    ApiResponse<?> getMenuItem(@PathVariable Long restaurantId, @PathVariable Long menuItemId);

    @PostMapping("/api/v1/restaurants/{restaurantId}/orders")
    ApiResponse<?> notifyNewOrder(@PathVariable Long restaurantId, @RequestBody OrderDTO orderDTO);
}