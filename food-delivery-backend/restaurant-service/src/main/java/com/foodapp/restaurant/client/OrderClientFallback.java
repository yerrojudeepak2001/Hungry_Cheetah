package com.foodapp.restaurant.client;

import org.springframework.stereotype.Component;
import com.foodapp.restaurant.dto.OrderResponse;
import java.util.Collections;
import java.util.List;

@Component
public class OrderClientFallback implements OrderClient {
    
    @Override
    public List<OrderResponse> getRestaurantOrders(Long restaurantId) {
        // Return empty list as fallback
        return Collections.emptyList();
    }
    
    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        // Return basic order response with error status
        OrderResponse response = new OrderResponse();
        response.setId(orderId);
        response.setStatus("ERROR");
        response.setMessage("Service temporarily unavailable");
        return response;
    }
}