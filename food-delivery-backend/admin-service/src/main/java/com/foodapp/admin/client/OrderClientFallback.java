package com.foodapp.admin.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.stereotype.Component;
import com.foodapp.admin.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Collections;
import java.util.List;

@Component
public class OrderClientFallback implements OrderClient {
    private static final Logger logger = LoggerFactory.getLogger(OrderClientFallback.class);

    @Override
    public long getTotalOrders() {
        logger.warn("Fallback: getTotalOrders - Order service unavailable");
        return 0L;
    }

    @Override
    public long getTodayOrders() {
        logger.warn("Fallback: getTodayOrders - Order service unavailable");
        return 0L;
    }

    @Override
    public long getMonthlyOrders() {
        logger.warn("Fallback: getMonthlyOrders - Order service unavailable");
        return 0L;
    }

    @Override
    public double getTotalRevenue() {
        logger.warn("Fallback: getTotalRevenue - Order service unavailable");
        return 0.0;
    }

    @Override
    public double getAverageOrderValue() {
        logger.warn("Fallback: getAverageOrderValue - Order service unavailable");
        return 0.0;
    }

    @Override
    public Page<OrderAdminInfo> getAllOrders(int page, int size, String sortBy, String sortDirection, String status, String search) {
        logger.warn("Fallback: getAllOrders - Order service unavailable");
        return new PageImpl<>(Collections.emptyList());
    }

    @Override
    public OrderAdminInfo getOrderById(String orderId) {
        logger.warn("Fallback: getOrderById - Order service unavailable for orderId: {}", orderId);
        return new OrderAdminInfo();
    }

    @Override
    public void updateOrderStatus(String orderId, String status, String reason) {
        logger.warn("Fallback: updateOrderStatus - Order service unavailable for orderId: {}", orderId);
    }

    @Override
    public void cancelOrder(String orderId, String reason) {
        logger.warn("Fallback: cancelOrder - Order service unavailable for orderId: {}", orderId);
    }

    @Override
    public void refundOrder(String orderId, double amount, String reason) {
        logger.warn("Fallback: refundOrder - Order service unavailable for orderId: {}", orderId);
    }

    @Override
    public List<OrderStatusHistory> getOrderHistory(String orderId) {
        logger.warn("Fallback: getOrderHistory - Order service unavailable for orderId: {}", orderId);
        return Collections.emptyList();
    }

    @Override
    public void clearCache() {
        logger.warn("Fallback: clearCache - Order service unavailable");
    }
}