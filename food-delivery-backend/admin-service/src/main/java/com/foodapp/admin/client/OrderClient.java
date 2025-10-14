package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.*;
import java.util.List;

@FeignClient(name = "ORDER-SERVICE", fallback = OrderClientFallback.class)
public interface OrderClient {
    
    // Statistics endpoints
    @GetMapping("/api/admin/orders/stats/total")
    long getTotalOrders();
    
    @GetMapping("/api/admin/orders/stats/today")
    long getTodayOrders();
    
    @GetMapping("/api/admin/orders/stats/monthly")
    long getMonthlyOrders();
    
    @GetMapping("/api/admin/orders/stats/revenue")
    double getTotalRevenue();
    
    @GetMapping("/api/admin/orders/stats/average-value")
    double getAverageOrderValue();
    
    // Order management endpoints
    @GetMapping("/api/admin/orders")
    Page<OrderAdminInfo> getAllOrders(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search
    );
    
    @GetMapping("/api/admin/orders/{orderId}")
    OrderAdminInfo getOrderById(@PathVariable("orderId") String orderId);
    
    @PutMapping("/api/admin/orders/{orderId}/status")
    void updateOrderStatus(
            @PathVariable("orderId") String orderId,
            @RequestParam String status,
            @RequestParam(required = false) String reason
    );
    
    @PutMapping("/api/admin/orders/{orderId}/cancel")
    void cancelOrder(
            @PathVariable("orderId") String orderId,
            @RequestParam String reason
    );
    
    @PostMapping("/api/admin/orders/{orderId}/refund")
    void refundOrder(
            @PathVariable("orderId") String orderId,
            @RequestParam double amount,
            @RequestParam String reason
    );
    
    @GetMapping("/api/admin/orders/{orderId}/history")
    List<OrderStatusHistory> getOrderHistory(@PathVariable("orderId") String orderId);
    
    @DeleteMapping("/api/admin/orders/cache")
    void clearCache();
}