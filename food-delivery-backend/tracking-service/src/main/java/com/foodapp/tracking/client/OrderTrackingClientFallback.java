package com.foodapp.tracking.client;

import com.foodapp.tracking.dto.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderTrackingClientFallback implements OrderTrackingClient {

    @Override
    public OrderTrackingInfo getOrderDeliveryInfo(String orderId) {
        return new OrderTrackingInfo(orderId, "unknown", 0.0, 0.0, 30, "unknown", "Unknown Driver", "N/A");
    }

    @Override
    public void updateOrderLocation(String orderId, LocationUpdate location) {
        // Fallback: do nothing
    }

    @Override
    public List<OrderTrackingInfo> getActiveDeliveries(String regionId) {
        return List.of();
    }

    @Override
    public void updateEstimatedArrival(String orderId, ArrivalUpdate update) {
        // Fallback: do nothing
    }
}