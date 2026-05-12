package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.*;
import java.util.List;

@FeignClient(name = "DELIVERY-SERVICE", fallback = DeliveryClientFallback.class)
public interface DeliveryClient {
    
    // Statistics endpoints
    @GetMapping("/api/admin/deliveries/stats/active")
    long getActiveDeliveries();
    
    @GetMapping("/api/admin/deliveries/stats/completed")
    long getCompletedDeliveries();
    
    @GetMapping("/api/admin/deliveries/stats/today")
    long getTodayDeliveries();
    
    // Delivery management endpoints
    @GetMapping("/api/admin/deliveries")
    List<DeliveryAdminInfo> getAllDeliveries(@RequestParam int page, @RequestParam int size);
    
    @GetMapping("/api/admin/deliveries/{deliveryId}")
    DeliveryAdminInfo getDeliveryById(@PathVariable("deliveryId") String deliveryId);
    
    @PutMapping("/api/admin/deliveries/{deliveryId}/status")
    void updateDeliveryStatus(@PathVariable("deliveryId") String deliveryId, @RequestParam String status);
    
    @GetMapping("/api/admin/deliveries/drivers")
    List<DriverAdminInfo> getAllDrivers(@RequestParam int page, @RequestParam int size);
    
    @PutMapping("/api/admin/deliveries/drivers/{driverId}/status")
    void updateDriverStatus(@PathVariable("driverId") String driverId, @RequestParam String status);
    
    @DeleteMapping("/api/admin/deliveries/cache")
    void clearCache();
}