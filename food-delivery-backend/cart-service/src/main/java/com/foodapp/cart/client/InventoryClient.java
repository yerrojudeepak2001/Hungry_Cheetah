package com.foodapp.cart.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.cart.dto.InventoryCheck;
import com.foodapp.cart.dto.StockStatus;

@FeignClient(name = "INVENTORY-SERVICE", fallback = InventoryClientFallback.class)
public interface InventoryClient {
    @PostMapping("/api/inventory/check")
    List<StockStatus> checkItemsAvailability(@RequestBody InventoryCheck check);
    
    @GetMapping("/api/inventory/items/{itemId}")
    StockStatus getItemStock(@PathVariable("itemId") String itemId);
    
    @PostMapping("/api/inventory/reserve")
    boolean reserveItems(@RequestBody ReservationRequest request);
}