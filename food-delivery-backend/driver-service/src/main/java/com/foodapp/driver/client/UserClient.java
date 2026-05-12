package com.foodapp.driver.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.driver.dto.*;
import com.foodapp.driver.client.fallback.UserClientFallback;
import java.util.List;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/driver/{driverId}")
    UserProfile getDriverProfile(@PathVariable("driverId") String driverId);
    
    @PutMapping("/api/users/driver/{driverId}/location")
    void updateDriverLocation(@PathVariable("driverId") String driverId,
                            @RequestBody LocationUpdate location);
    
    @PutMapping("/api/users/driver/{driverId}/status")
    void updateDriverStatus(@PathVariable("driverId") String driverId,
                          @RequestBody DriverStatus status);
    
    @GetMapping("/api/users/driver/{driverId}/ratings")
    List<DriverRating> getDriverRatings(@PathVariable("driverId") String driverId);
}