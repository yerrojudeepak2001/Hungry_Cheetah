package com.foodapp.user.client;

import com.foodapp.user.dto.OrderResponse;
import com.foodapp.user.dto.UserOrderStats;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public List<OrderResponse> getUserOrders(String userId) {
        return Collections.emptyList();
    }

    @Override
    public List<OrderResponse> getUserActiveOrders(String userId) {
        return Collections.emptyList();
    }

    @Override
    public UserOrderStats getUserOrderStats(String userId) {
        return new UserOrderStats();
    }
}