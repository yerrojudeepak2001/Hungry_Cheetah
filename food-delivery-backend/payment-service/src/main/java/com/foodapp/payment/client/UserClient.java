package com.foodapp.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.payment.dto.UserPaymentInfo;

@FeignClient(name = "USER-SERVICE")
public interface UserClient {
    @GetMapping("/api/users/{userId}/payment-info")
    UserPaymentInfo getUserPaymentInfo(@PathVariable("userId") String userId);
    
    @GetMapping("/api/users/{userId}/payment-methods")
    List<PaymentMethod> getUserPaymentMethods(@PathVariable("userId") String userId);
    
    @PostMapping("/api/users/{userId}/payment-methods")
    void addPaymentMethod(@PathVariable("userId") String userId, 
                         @RequestBody PaymentMethod paymentMethod);
}