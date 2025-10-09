package com.foodapp.driver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.driver.dto.DeliveryAssignment;
import com.foodapp.driver.dto.DeliveryStatus;

@FeignClient(name = "DELIVERY-SERVICE", fallback = DeliveryClientFallback.class)
public interface DeliveryClient {
    @GetMapping("/api/delivery/assignments/{driverId}")
    List<DeliveryAssignment> getDriverAssignments(@PathVariable("driverId") String driverId);
    
    @PutMapping("/api/delivery/{deliveryId}/status")
    void updateDeliveryStatus(@PathVariable("deliveryId") String deliveryId,
                            @RequestBody DeliveryStatus status);
    
    @PostMapping("/api/delivery/availability")
    void updateDriverAvailability(@RequestBody DriverAvailability availability);
    
    @GetMapping("/api/delivery/optimal-route")
    List<RoutePoint> getOptimalRoute(@RequestParam List<String> deliveryIds);
}