package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.POSIntegrationClient;
import com.foodapp.restaurant.dto.POSMenuSync;
import com.foodapp.restaurant.dto.POSOrderData;
import com.foodapp.restaurant.dto.POSIntegrationStatus;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Collections;

@Component
public class POSIntegrationClientFallback implements POSIntegrationClient {

    @Override
    public void synchronizeMenu(String restaurantId, POSMenuSync menuData) {
        // Fallback: Do nothing
    }

    @Override
    public Map<String, Integer> getInventoryLevels(String restaurantId) {
        return Collections.emptyMap();
    }

    @Override
    public void pushOrderToPOS(String restaurantId, POSOrderData orderData) {
        // Fallback: Do nothing
    }

    @Override
    public POSIntegrationStatus getIntegrationStatus(String restaurantId) {
        return POSIntegrationStatus.builder()
                .status("DISCONNECTED")
                .errorMessage("Service unavailable")
                .build();
    }
}