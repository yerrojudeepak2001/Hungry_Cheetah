package com.foodapp.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.delivery.dto.DriverAssignmentRequest;
import com.foodapp.delivery.dto.DriverStatus;

@FeignClient(name = "DRIVER-SERVICE", fallback = DriverClientFallback.class)
public interface DriverClient {
    @PostMapping("/api/drivers/assign")
    void assignDelivery(@RequestBody DriverAssignmentRequest request);
    
    @GetMapping("/api/drivers/{driverId}/status")
    DriverStatus getDriverStatus(@PathVariable("driverId") String driverId);
    
    @GetMapping("/api/drivers/available/{locationId}")
    List<DriverStatus> getAvailableDrivers(@PathVariable("locationId") String locationId);
    
    @PutMapping("/api/drivers/{driverId}/location")
    void updateDriverLocation(@PathVariable("driverId") String driverId,
                            @RequestBody LocationUpdate location);
}