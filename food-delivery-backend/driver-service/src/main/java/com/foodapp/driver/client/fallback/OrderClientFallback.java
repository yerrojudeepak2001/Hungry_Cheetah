package com.foodapp.driver.client.fallback;

import com.foodapp.driver.client.OrderClient;
import com.foodapp.driver.dto.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;
import java.math.BigDecimal;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public OrderDetails getOrderDetails(String orderId) {
        // Return default order details when order service is unavailable
        OrderDetails defaultOrder = new OrderDetails();
        defaultOrder.setOrderId(orderId);
        defaultOrder.setCustomerId(0L);
        defaultOrder.setRestaurantId(0L);
        defaultOrder.setRestaurantName("Service Unavailable");
        defaultOrder.setRestaurantAddress("Unknown");
        defaultOrder.setDeliveryAddress("Unknown");
        defaultOrder.setCustomerName("Unknown Customer");
        defaultOrder.setCustomerPhone("000-000-0000");
        defaultOrder.setItems(new ArrayList<>());
        defaultOrder.setTotalAmount(BigDecimal.ZERO);
        defaultOrder.setOrderStatus("UNKNOWN");
        defaultOrder.setOrderTime(LocalDateTime.now());
        defaultOrder.setSpecialInstructions("Order service unavailable");
        return defaultOrder;
    }

    @Override
    public void updateDriverStatus(String orderId, DriverStatusUpdate status) {
        // Do nothing when order service is unavailable
    }

    @Override
    public List<OrderDetails> getDriverOrderHistory(String driverId) {
        // Return empty list when order service is unavailable
        return new ArrayList<>();
    }

    @Override
    public void reportDeliveryIssue(String orderId, DeliveryIssue issue) {
        // Do nothing when order service is unavailable
    }
}