package com.foodapp.admin.controller;

import com.foodapp.admin.dto.ApiResponse;
import com.foodapp.admin.dto.OrderAdminInfo;
import com.foodapp.admin.dto.OrderStatusHistory;
import com.foodapp.admin.service.OrderServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin/orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class OrderManagementController {

    private final OrderServiceClient orderServiceClient;

    @GetMapping("/stats")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<OrderStats> getOrderStats() {
        OrderStats stats = OrderStats.builder()
            .totalOrders(orderServiceClient.getTotalOrders())
            .todayOrders(orderServiceClient.getTodayOrders())
            .monthlyOrders(orderServiceClient.getMonthlyOrders())
            .totalRevenue(orderServiceClient.getTotalRevenue())
            .averageOrderValue(orderServiceClient.getAverageOrderValue())
            .build();
        
        return new ApiResponse<>(true, "Order statistics retrieved successfully", stats);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Page<OrderAdminInfo>> getAllOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "orderDate") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDirection,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String search) {
        
        Page<OrderAdminInfo> orders = orderServiceClient.getAllOrders(page, size, sortBy, sortDirection, status, search);
        return new ApiResponse<>(true, "Orders retrieved successfully", orders);
    }

    @GetMapping("/{orderId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<OrderAdminInfo> getOrderById(@PathVariable String orderId) {
        OrderAdminInfo order = orderServiceClient.getOrderById(orderId);
        return new ApiResponse<>(true, "Order retrieved successfully", order);
    }

    @PutMapping("/{orderId}/status")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> updateOrderStatus(
            @PathVariable String orderId,
            @RequestParam String status,
            @RequestParam(required = false) String reason) {
        
        orderServiceClient.updateOrderStatus(orderId, status, reason);
        return new ApiResponse<>(true, "Order status updated successfully", null);
    }

    @PostMapping("/{orderId}/cancel")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> cancelOrder(
            @PathVariable String orderId,
            @RequestBody CancelOrderRequest request) {
        
        orderServiceClient.cancelOrder(orderId, request.getReason());
        return new ApiResponse<>(true, "Order cancelled successfully", null);
    }

    @PostMapping("/{orderId}/refund")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> refundOrder(
            @PathVariable String orderId,
            @RequestBody RefundOrderRequest request) {
        
        orderServiceClient.refundOrder(orderId, request.getAmount(), request.getReason());
        return new ApiResponse<>(true, "Order refund processed successfully", null);
    }

    @GetMapping("/{orderId}/history")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<List<OrderStatusHistory>> getOrderHistory(@PathVariable String orderId) {
        List<OrderStatusHistory> history = orderServiceClient.getOrderHistory(orderId);
        return new ApiResponse<>(true, "Order history retrieved successfully", history);
    }

    @PostMapping("/cache/clear")
    @PreAuthorize("hasRole('ADMIN')")
    public ApiResponse<Void> clearOrderCache() {
        orderServiceClient.clearCache();
        return new ApiResponse<>(true, "Order service cache cleared successfully", null);
    }

    // Inner classes for request/response DTOs
    @lombok.Data
    @lombok.Builder
    public static class OrderStats {
        private long totalOrders;
        private long todayOrders;
        private long monthlyOrders;
        private double totalRevenue;
        private double averageOrderValue;
    }

    @lombok.Data
    public static class CancelOrderRequest {
        private String reason;
    }

    @lombok.Data
    public static class RefundOrderRequest {
        private double amount;
        private String reason;
    }
}