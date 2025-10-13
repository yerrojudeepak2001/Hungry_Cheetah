package com.foodapp.cart.client.fallback;

import com.foodapp.cart.client.OrderClient;
import com.foodapp.cart.dto.OrderRequest;
import com.foodapp.cart.dto.OrderResponse;
import org.springframework.stereotype.Component;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public OrderResponse createOrder(OrderRequest request) {
        OrderResponse response = new OrderResponse();
        response.setOrderId("fallback-order");
        response.setStatus("FAILED");
        response.setMessage("Order service unavailable");
        return response;
    }

    @Override
    public boolean validateOrderItems(java.util.List<String> itemIds) {
        return false;
    }

    @Override
    public int getEstimatedPreparationTime(java.util.List<String> itemIds) {
        return 30; // default 30 minutes
    }
}