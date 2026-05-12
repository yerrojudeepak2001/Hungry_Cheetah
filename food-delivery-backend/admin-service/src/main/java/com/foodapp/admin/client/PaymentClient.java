package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.*;
import java.util.List;

@FeignClient(name = "PAYMENT-SERVICE", fallback = PaymentClientFallback.class)
public interface PaymentClient {
    
    // Statistics endpoints
    @GetMapping("/api/admin/payments/stats/pending")
    long getPendingPayments();
    
    @GetMapping("/api/admin/payments/stats/revenue")
    double getTotalRevenue();
    
    @GetMapping("/api/admin/payments/stats/today-revenue")
    double getTodayRevenue();
    
    @GetMapping("/api/admin/payments/stats/monthly-revenue")
    double getMonthlyRevenue();
    
    @GetMapping("/api/admin/payments/stats/failed")
    long getFailedPayments();
    
    // Payment management endpoints
    @GetMapping("/api/admin/payments")
    List<PaymentAdminInfo> getAllPayments(@RequestParam int page, @RequestParam int size);
    
    @GetMapping("/api/admin/payments/{paymentId}")
    PaymentAdminInfo getPaymentById(@PathVariable("paymentId") String paymentId);
    
    @PostMapping("/api/admin/payments/{paymentId}/refund")
    void processRefund(@PathVariable("paymentId") String paymentId, @RequestParam double amount, @RequestParam String reason);
    
    @PutMapping("/api/admin/payments/{paymentId}/status")
    void updatePaymentStatus(@PathVariable("paymentId") String paymentId, @RequestParam String status);
    
    @GetMapping("/api/admin/payments/failed")
    List<PaymentAdminInfo> getFailedPaymentsList(@RequestParam int page, @RequestParam int size);
    
    @PostMapping("/api/admin/payments/{paymentId}/retry")
    void retryPayment(@PathVariable("paymentId") String paymentId);
    
    @DeleteMapping("/api/admin/payments/cache")
    void clearCache();
}