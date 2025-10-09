package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.restaurant.dto.InventoryUpdate;
import com.foodapp.restaurant.dto.StockLevel;

@FeignClient(name = "INVENTORY-SERVICE", fallback = InventoryClientFallback.class)
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