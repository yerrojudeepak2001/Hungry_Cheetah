package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.InventoryClient;
import com.foodapp.restaurant.dto.InventoryUpdate;
import com.foodapp.restaurant.dto.inventory.StockLevel;
import com.foodapp.restaurant.dto.inventory.InventoryAlert;
import com.foodapp.restaurant.dto.inventory.InventoryForecast;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Collections;

@Component
public class InventoryClientFallback implements InventoryClient {

    @Override
    public List<StockLevel> getCurrentStock(String restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public void updateInventoryLevels(InventoryUpdate update) {
        // Fallback: Do nothing
    }

    @Override
    public List<InventoryAlert> getLowStockAlerts(String restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public void updateInventoryForecast(InventoryForecast forecast) {
        // Fallback: Do nothing
    }
}