package com.foodapp.loyalty.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.loyalty.dto.UserLoyaltyInfo;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}/loyalty")
    UserLoyaltyInfo getUserLoyaltyInfo(@PathVariable("userId") String userId);
    
    @PutMapping("/api/users/{userId}/loyalty-points")
    void updateUserLoyaltyPoints(@PathVariable("userId") String userId, @RequestParam int points);
    
    @PutMapping("/api/users/{userId}/loyalty-tier")
    void updateUserLoyaltyTier(@PathVariable("userId") String userId, @RequestParam String tier);
}