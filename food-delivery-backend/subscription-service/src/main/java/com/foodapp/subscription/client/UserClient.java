package com.foodapp.subscription.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.subscription.dto.UserSubscriptionInfo;

@FeignClient(name = "USER-SERVICE", fallback = UserClientFallback.class)
public interface UserClient {
    @GetMapping("/api/users/{userId}/subscription")
    UserSubscriptionInfo getUserSubscriptionInfo(@PathVariable("userId") String userId);
    
    @PutMapping("/api/users/{userId}/subscription")
    void updateUserSubscription(@PathVariable("userId") String userId, 
                              @RequestBody SubscriptionUpdate update);
    
    @GetMapping("/api/users/{userId}/payment-methods")
    List<PaymentMethod> getUserPaymentMethods(@PathVariable("userId") String userId);
}