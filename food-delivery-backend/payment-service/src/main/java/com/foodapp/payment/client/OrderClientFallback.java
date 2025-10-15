package com.foodapp.payment.client;

import org.springframework.stereotype.Component;
import com.foodapp.payment.dto.OrderDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.math.BigDecimal;

@Component
public class OrderClientFallback implements OrderClient {
    
    private static final Logger log = LoggerFactory.getLogger(OrderClientFallback.class);
    
    @Override
    public OrderDetails getOrderDetails(Long orderId) {
        // Return basic order details with error status
        OrderDetails details = new OrderDetails();
        details.setId(orderId);
        details.setStatus("ERROR");
        details.setTotalAmount(BigDecimal.ZERO);
        log.error("Failed to get order details for order: {}", orderId);
        return details;
    }
    
    @Override
    public void updatePaymentStatus(Long orderId, String status) {
        // Log the failure and potentially queue for retry
        log.error("Failed to update payment status for order: {} with status: {}", orderId, status);
    }
}