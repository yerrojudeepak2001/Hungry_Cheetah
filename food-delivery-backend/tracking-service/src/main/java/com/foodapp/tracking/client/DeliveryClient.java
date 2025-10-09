package com.foodapp.tracking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.tracking.dto.DeliveryTrackingInfo;

@FeignClient(name = "DELIVERY-SERVICE", fallback = DeliveryClientFallback.class)
public interface DeliveryClient {
    @GetMapping("/api/delivery/{deliveryId}/location")
    DeliveryLocation getCurrentLocation(@PathVariable("deliveryId") String deliveryId);
    
    @GetMapping("/api/delivery/{deliveryId}/route")
    List<RoutePoint> getDeliveryRoute(@PathVariable("deliveryId") String deliveryId);
    
    @GetMapping("/api/delivery/{deliveryId}/eta")
    int getUpdatedETA(@PathVariable("deliveryId") String deliveryId);
    
    @PutMapping("/api/delivery/{deliveryId}/tracking")
    void updateTrackingInfo(@PathVariable("deliveryId") String deliveryId, 
                           @RequestBody TrackingUpdate update);
}