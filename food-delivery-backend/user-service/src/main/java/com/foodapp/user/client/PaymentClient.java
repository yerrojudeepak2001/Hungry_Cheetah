package com.foodapp.user.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.user.dto.PaymentMethodDto;
import com.foodapp.user.dto.PaymentHistoryDto;
import java.util.List;

@FeignClient(name = "PAYMENT-SERVICE", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    
    @GetMapping("/api/payments/user/{userId}/methods")
    List<PaymentMethodDto> getUserPaymentMethods(@PathVariable("userId") String userId);
    
    @PostMapping("/api/payments/user/{userId}/methods")
    PaymentMethodDto addPaymentMethod(
            @PathVariable("userId") String userId,
            @RequestBody PaymentMethodDto paymentMethod);
    
    @DeleteMapping("/api/payments/user/{userId}/methods/{methodId}")
    void removePaymentMethod(
            @PathVariable("userId") String userId,
            @PathVariable("methodId") String methodId);
    
    @GetMapping("/api/payments/user/{userId}/history")
    List<PaymentHistoryDto> getPaymentHistory(@PathVariable("userId") String userId);
    
    @PostMapping("/api/payments/user/{userId}/methods/{methodId}/set-default")
    void setDefaultPaymentMethod(
            @PathVariable("userId") String userId,
            @PathVariable("methodId") String methodId);
    
    @GetMapping("/api/payments/user/{userId}/wallet-balance")
    Double getWalletBalance(@PathVariable("userId") String userId);
    
    @PostMapping("/api/payments/user/{userId}/wallet/add-funds")
    void addFundsToWallet(
            @PathVariable("userId") String userId,
            @RequestParam Double amount);
}