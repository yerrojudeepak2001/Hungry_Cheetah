package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.foodapp.restaurant.dto.InventoryUpdate;
import com.foodapp.restaurant.dto.inventory.StockLevel;
import com.foodapp.restaurant.dto.inventory.InventoryAlert;
import com.foodapp.restaurant.dto.inventory.InventoryForecast;

@FeignClient(name = "INVENTORY-SERVICE", fallback = com.foodapp.restaurant.client.fallback.InventoryClientFallback.class)
public interface InventoryClient {
    @GetMapping("/api/inventory/restaurant/{restaurantId}/stock")
    List<StockLevel> getCurrentStock(@PathVariable("restaurantId") String restaurantId);
    
    @PostMapping("/api/inventory/update")
    void updateInventoryLevels(@RequestBody InventoryUpdate update);
    
    @GetMapping("/api/inventory/alerts/{restaurantId}")
    List<InventoryAlert> getLowStockAlerts(@PathVariable("restaurantId") String restaurantId);
    
    @PostMapping("/api/inventory/forecast")
    void updateInventoryForecast(@RequestBody InventoryForecast forecast);
}