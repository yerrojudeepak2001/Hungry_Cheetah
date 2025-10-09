package com.foodapp.kitchen.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.kitchen.dto.InventoryCheck;

@FeignClient(name = "INVENTORY-SERVICE", fallback = InventoryClientFallback.class)
public interface InventoryClient {
    @GetMapping("/api/inventory/kitchen/{kitchenId}/check")
    InventoryCheck checkIngredients(@PathVariable("kitchenId") String kitchenId,
                                  @RequestBody List<String> ingredientIds);
    
    @PostMapping("/api/inventory/kitchen/{kitchenId}/consume")
    void consumeIngredients(@PathVariable("kitchenId") String kitchenId,
                           @RequestBody IngredientConsumption consumption);
    
    @GetMapping("/api/inventory/kitchen/{kitchenId}/alerts")
    List<InventoryAlert> getLowStockAlerts(@PathVariable("kitchenId") String kitchenId);
    
    @GetMapping("/api/inventory/kitchen/{kitchenId}/forecast")
    InventoryForecast getInventoryForecast(@PathVariable("kitchenId") String kitchenId);
}