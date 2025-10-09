package com.foodapp.driver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.driver.dto.OrderDetails;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    @GetMapping("/api/orders/{orderId}")
    OrderDetails getOrderDetails(@PathVariable("orderId") String orderId);
    
    @PutMapping("/api/orders/{orderId}/driver-status")
    void updateDriverStatus(@PathVariable("orderId") String orderId,
                          @RequestBody DriverStatusUpdate status);
    
    @GetMapping("/api/orders/driver/{driverId}/history")
    List<OrderDetails> getDriverOrderHistory(@PathVariable("driverId") String driverId);
    
    @PostMapping("/api/orders/{orderId}/delivery-issues")
    void reportDeliveryIssue(@PathVariable("orderId") String orderId,
                            @RequestBody DeliveryIssue issue);
}