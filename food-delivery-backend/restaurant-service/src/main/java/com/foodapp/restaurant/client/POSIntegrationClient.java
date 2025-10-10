package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import com.foodapp.restaurant.dto.POSMenuSync;

@FeignClient(name = "POS-INTEGRATION-SERVICE", fallback = POSIntegrationClientFallback.class)
public interface POSIntegrationClient {
    @PostMapping("/api/pos/{restaurantId}/menu/sync")
    void synchronizeMenu(@PathVariable("restaurantId") String restaurantId, 
                        @RequestBody POSMenuSync menuData);
    
    @GetMapping("/api/pos/{restaurantId}/inventory")
    Map<String, Integer> getInventoryLevels(@PathVariable("restaurantId") String restaurantId);
    
    @PostMapping("/api/pos/{restaurantId}/order")
    void pushOrderToPOS(@PathVariable("restaurantId") String restaurantId, 
                       @RequestBody POSOrderData orderData);
                       
    @GetMapping("/api/pos/{restaurantId}/status")
    POSIntegrationStatus getIntegrationStatus(@PathVariable("restaurantId") String restaurantId);
}