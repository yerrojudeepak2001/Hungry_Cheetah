package com.foodapp.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.user.dto.DeliveryAddressDto;
import com.foodapp.user.dto.DeliveryTrackingDto;
import java.util.List;

@FeignClient(name = "DELIVERY-SERVICE", fallback = DeliveryClientFallback.class)
public interface DeliveryClient {
    
    @GetMapping("/api/delivery/user/{userId}/active")
    List<DeliveryTrackingDto> getActiveDeliveries(@PathVariable("userId") String userId);
    
    @GetMapping("/api/delivery/user/{userId}/history")
    List<DeliveryTrackingDto> getDeliveryHistory(@PathVariable("userId") String userId);
    
    @GetMapping("/api/delivery/track/{deliveryId}")
    DeliveryTrackingDto trackDelivery(@PathVariable("deliveryId") String deliveryId);
    
    @PostMapping("/api/delivery/user/{userId}/address/validate")
    Boolean validateDeliveryAddress(
            @PathVariable("userId") String userId,
            @RequestBody DeliveryAddressDto address);
    
    @GetMapping("/api/delivery/user/{userId}/delivery-time-estimate")
    Integer getDeliveryTimeEstimate(
            @PathVariable("userId") String userId,
            @RequestParam String restaurantId,
            @RequestParam String addressId);
    
    @PostMapping("/api/delivery/user/{userId}/rate-delivery/{deliveryId}")
    void rateDelivery(
            @PathVariable("userId") String userId,
            @PathVariable("deliveryId") String deliveryId,
            @RequestParam Integer rating,
            @RequestParam(required = false) String feedback);
}