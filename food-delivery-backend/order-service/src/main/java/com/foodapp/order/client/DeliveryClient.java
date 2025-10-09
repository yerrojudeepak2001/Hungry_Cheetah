package com.foodapp.order.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.foodapp.order.dto.DeliveryRequest;
import com.foodapp.order.dto.DeliveryResponse;

@FeignClient(name = "DELIVERY-SERVICE")
public interface DeliveryClient {
    
    @PostMapping("/api/delivery/assign")
    DeliveryResponse assignDelivery(@RequestBody DeliveryRequest deliveryRequest);
    
    @GetMapping("/api/delivery/{orderId}/track")
    DeliveryResponse trackDelivery(@PathVariable("orderId") Long orderId);
    
    @GetMapping("/api/delivery/{orderId}/eta")
    int getEstimatedDeliveryTime(@PathVariable("orderId") Long orderId);
}