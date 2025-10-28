package com.foodapp.tracking.client;

import com.foodapp.tracking.dto.*;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class DeliveryClientFallback implements DeliveryClient {

    @Override
    public DeliveryLocation getCurrentLocation(String deliveryId) {
        return new DeliveryLocation(deliveryId, 0.0, 0.0, "Unknown", System.currentTimeMillis());
    }

    @Override
    public List<RoutePoint> getDeliveryRoute(String deliveryId) {
        return List.of();
    }

    @Override
    public int getUpdatedETA(String deliveryId) {
        return 30; // Default 30 minutes
    }

    @Override
    public void updateTrackingInfo(String deliveryId, TrackingUpdate update) {
        // Fallback: do nothing
    }
}