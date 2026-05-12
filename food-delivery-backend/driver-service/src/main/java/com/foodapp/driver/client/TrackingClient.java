package com.foodapp.driver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.driver.dto.*;
import com.foodapp.driver.client.fallback.TrackingClientFallback;
import java.time.LocalDateTime;
import java.util.List;

@FeignClient(name = "TRACKING-SERVICE", fallback = TrackingClientFallback.class)
public interface TrackingClient {
    @PostMapping("/api/tracking/driver/{driverId}/location")
    void updateDriverLocation(@PathVariable("driverId") String driverId,
                            @RequestBody LocationUpdate location);
    
    @GetMapping("/api/tracking/driver/{driverId}/route")
    RouteInfo getCurrentRoute(@PathVariable("driverId") String driverId);
    
    @GetMapping("/api/tracking/driver/{driverId}/history")
    List<TrackingInfo> getLocationHistory(@PathVariable("driverId") String driverId,
                                        @RequestParam LocalDateTime startTime,
                                        @RequestParam LocalDateTime endTime);
    
    @PostMapping("/api/tracking/driver/{driverId}/status")
    void updateDriverStatus(@PathVariable("driverId") String driverId,
                          @RequestBody StatusUpdate status);
}