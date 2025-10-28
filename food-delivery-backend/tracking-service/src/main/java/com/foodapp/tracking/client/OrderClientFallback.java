package com.foodapp.tracking.client;

import com.foodapp.tracking.dto.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public OrderTrackingInfo getOrderTrackingInfo(String orderId) {
        return new OrderTrackingInfo(orderId, "unknown", 0.0, 0.0, 30, "unknown", "Unknown Driver", "N/A");
    }

    @Override
    public void updateOrderTrackingStatus(String orderId, TrackingStatusUpdate update) {
        // Fallback: do nothing
    }

    @Override
    public List<OrderTimelineEvent> getOrderTimeline(String orderId) {
        return List.of();
    }
}