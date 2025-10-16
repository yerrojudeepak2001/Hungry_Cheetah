package com.foodapp.driver.client.fallback;

import com.foodapp.driver.client.DeliveryClient;
import com.foodapp.driver.dto.*;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class DeliveryClientFallback implements DeliveryClient {

    @Override
    public List<DeliveryAssignment> getDriverAssignments(String driverId) {
        // Return empty list when delivery service is unavailable
        return new ArrayList<>();
    }

    @Override
    public void updateDeliveryStatus(String deliveryId, DeliveryStatus status) {
        // Do nothing when delivery service is unavailable
        // In a real implementation, you might queue this for later processing
    }

    @Override
    public void updateDriverAvailability(DriverAvailability availability) {
        // Do nothing when delivery service is unavailable
    }

    @Override
    public List<RoutePoint> getOptimalRoute(List<String> deliveryIds) {
        // Return empty list when delivery service is unavailable
        return new ArrayList<>();
    }
}