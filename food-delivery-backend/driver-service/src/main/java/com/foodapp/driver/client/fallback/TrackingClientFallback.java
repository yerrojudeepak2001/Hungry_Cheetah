package com.foodapp.driver.client.fallback;

import com.foodapp.driver.client.TrackingClient;
import com.foodapp.driver.dto.*;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ArrayList;

@Component
public class TrackingClientFallback implements TrackingClient {

    @Override
    public void updateDriverLocation(String driverId, LocationUpdate location) {
        // Do nothing when tracking service is unavailable
    }

    @Override
    public RouteInfo getCurrentRoute(String driverId) {
        // Return default route info when tracking service is unavailable
        RouteInfo defaultRoute = new RouteInfo();
        defaultRoute.setRouteId("fallback-route");
        defaultRoute.setWaypoints(new ArrayList<>());
        defaultRoute.setTotalDistance(0.0);
        defaultRoute.setEstimatedDuration(0);
        defaultRoute.setTrafficCondition("UNKNOWN");
        defaultRoute.setAlternativeRoutes(new ArrayList<>());
        return defaultRoute;
    }

    @Override
    public List<TrackingInfo> getLocationHistory(String driverId, LocalDateTime startTime, LocalDateTime endTime) {
        // Return empty list when tracking service is unavailable
        return new ArrayList<>();
    }

    @Override
    public void updateDriverStatus(String driverId, StatusUpdate status) {
        // Do nothing when tracking service is unavailable
    }
}