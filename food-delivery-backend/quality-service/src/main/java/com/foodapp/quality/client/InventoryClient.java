package com.foodapp.quality.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.quality.dto.InventoryQualityCheck;

@FeignClient(name = "INVENTORY-SERVICE", fallback = InventoryClientFallback.class)
public interface InventoryClient {
    @GetMapping("/api/inventory/items/{itemId}/quality")
    InventoryQualityCheck checkItemQuality(@PathVariable("itemId") String itemId);
    
    @PostMapping("/api/inventory/items/quality-report")
    void reportQualityIssue(@RequestBody QualityIssueReport report);
    
    @GetMapping("/api/inventory/items/expiring")
    List<ExpiringItem> getExpiringItems(@RequestParam String locationId);
    
    @GetMapping("/api/inventory/items/storage-conditions")
    StorageConditions getStorageConditions(@RequestParam String locationId);
}