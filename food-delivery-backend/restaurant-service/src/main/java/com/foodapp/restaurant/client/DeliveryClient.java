package com.foodapp.restaurant.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.restaurant.dto.DeliveryTimeResponse;
import java.util.List;

@FeignClient(name = "DELIVERY-SERVICE")
public interface DeliveryClient {
    @GetMapping("/api/delivery/restaurant/{restaurantId}/active")
    List<DeliveryTimeResponse> getActiveDeliveries(@PathVariable("restaurantId") String restaurantId);
    
    @GetMapping("/api/delivery/restaurant/{restaurantId}/eta")
    int getEstimatedDeliveryTime(@PathVariable("restaurantId") String restaurantId, 
                                @RequestParam("latitude") double latitude,
                                @RequestParam("longitude") double longitude);
}