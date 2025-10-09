package com.foodapp.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.delivery.dto.DeliverySchedule;
import com.foodapp.delivery.dto.TimeSlot;

@FeignClient(name = "SCHEDULING-SERVICE", fallback = SchedulingClientFallback.class)
public interface SchedulingClient {
    @PostMapping("/api/schedule/delivery")
    TimeSlot scheduleDelivery(@RequestBody DeliverySchedule schedule);
    
    @GetMapping("/api/schedule/available-slots/{locationId}")
    List<TimeSlot> getAvailableTimeSlots(@PathVariable("locationId") String locationId);
    
    @PutMapping("/api/schedule/delivery/{deliveryId}")
    void updateDeliverySchedule(@PathVariable("deliveryId") String deliveryId,
                              @RequestBody DeliverySchedule schedule);
    
    @GetMapping("/api/schedule/optimized-route/{driverId}")
    List<DeliveryRoute> getOptimizedRoute(@PathVariable("driverId") String driverId);
}