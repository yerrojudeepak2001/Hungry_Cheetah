package com.foodapp.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.delivery.dto.ExternalDeliveryRequest;
import com.foodapp.delivery.dto.PartnerAvailability;

@FeignClient(name = "EXTERNAL-DELIVERY-SERVICE", fallback = ExternalDeliveryClientFallback.class)
public interface ExternalDeliveryClient {
    @PostMapping("/api/external/delivery/request")
    void requestExternalDelivery(@RequestBody ExternalDeliveryRequest request);
    
    @GetMapping("/api/external/partners/{locationId}")
    List<PartnerAvailability> getAvailablePartners(@PathVariable("locationId") String locationId);
    
    @GetMapping("/api/external/delivery/{trackingId}")
    DeliveryStatus getExternalDeliveryStatus(@PathVariable("trackingId") String trackingId);
    
    @PostMapping("/api/external/delivery/{deliveryId}/cancel")
    void cancelExternalDelivery(@PathVariable("deliveryId") String deliveryId);
}