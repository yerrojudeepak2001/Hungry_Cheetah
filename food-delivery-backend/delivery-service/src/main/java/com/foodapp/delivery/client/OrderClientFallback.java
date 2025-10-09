package com.foodapp.delivery.client;

import org.springframework.stereotype.Component;
import com.foodapp.delivery.dto.OrderDetails;

@Component
public class OrderClientFallback implements OrderClient {
    
    @Override
    public OrderDetails getOrderDetails(Long orderId) {
        // Return basic order details with error status
        OrderDetails details = new OrderDetails();
        details.setOrderId(orderId);
        details.setStatus("ERROR");
        details.setMessage("Service temporarily unavailable");
        return details;
    }
    
    @Override
    public void updateDeliveryStatus(Long orderId, String status) {
        // Log the failure and potentially queue for retry
        log.error("Failed to update delivery status for order: {}", orderId);
    }
}