package com.foodapp.notification.client;

import com.foodapp.notification.dto.OrderDetails;
import com.foodapp.notification.dto.OrderStatusChange;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
public class OrderClientFallback implements OrderClient {
    
    @Override
    public OrderDetails getOrderDetails(String orderId) {
        log.warn("OrderClient fallback: getOrderDetails called for order {}", orderId);
        return OrderDetails.builder()
            .orderId(orderId)
            .userId(1L)
            .status("unknown")
            .totalAmount(0.0)
            .restaurantName("Unknown Restaurant")
            .items(Collections.emptyList())
            .deliveryAddress("Unknown Address")
            .orderDate(LocalDateTime.now())
            .estimatedDeliveryTime(LocalDateTime.now().plusMinutes(30))
            .driverName("Unknown Driver")
            .driverPhone("N/A")
            .build();
    }
    
    @Override
    public List<OrderStatusChange> getOrderStatusHistory(String orderId) {
        log.warn("OrderClient fallback: getOrderStatusHistory called for order {}", orderId);
        return Collections.emptyList();
    }
}