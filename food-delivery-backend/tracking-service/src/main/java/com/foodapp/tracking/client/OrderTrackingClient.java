package com.foodapp.tracking.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.tracking.dto.OrderTrackingInfo;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderTrackingClient {
    @GetMapping("/api/orders/{orderId}/delivery-info")
    OrderTrackingInfo getOrderDeliveryInfo(@PathVariable("orderId") String orderId);
    
    @PutMapping("/api/orders/{orderId}/location")
    void updateOrderLocation(@PathVariable("orderId") String orderId,
                           @RequestBody LocationUpdate location);
    
    @GetMapping("/api/orders/active-deliveries")
    List<OrderTrackingInfo> getActiveDeliveries(@RequestParam String regionId);
    
    @PutMapping("/api/orders/{orderId}/estimated-arrival")
    void updateEstimatedArrival(@PathVariable("orderId") String orderId,
                               @RequestBody ArrivalUpdate update);
}