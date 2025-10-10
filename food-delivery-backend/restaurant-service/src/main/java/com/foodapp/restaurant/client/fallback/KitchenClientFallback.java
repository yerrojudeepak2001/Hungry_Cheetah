package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.KitchenClient;
import com.foodapp.restaurant.dto.kitchen.KitchenOrder;
import com.foodapp.restaurant.dto.kitchen.KitchenStatus;
import com.foodapp.restaurant.dto.kitchen.KitchenStatusUpdate;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Collections;

@Component
public class KitchenClientFallback implements KitchenClient {

    @Override
    public void sendToKitchen(KitchenOrder order) {
        // Fallback: Do nothing
    }

    @Override
    public KitchenStatus getOrderStatus(String orderId) {
        return KitchenStatus.builder()
                .status("UNAVAILABLE")
                .queueLength(0)
                .estimatedWaitTime(0)
                .build();
    }

    @Override
    public Map<String, Object> getCurrentCapacity(String restaurantId) {
        return Collections.emptyMap();
    }

    @Override
    public void updateOrderStatus(String orderId, KitchenStatusUpdate update) {
        // Fallback: Do nothing
    }
}