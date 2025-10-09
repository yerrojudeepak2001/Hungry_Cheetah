package com.foodapp.payment.client;

import org.springframework.stereotype.Component;
import com.foodapp.payment.dto.OrderDetails;

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
    public void updatePaymentStatus(Long orderId, String status) {
        // Log the failure and potentially queue for retry
        log.error("Failed to update payment status for order: {}", orderId);
    }
}