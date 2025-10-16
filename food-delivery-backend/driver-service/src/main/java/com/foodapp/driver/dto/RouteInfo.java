package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RouteInfo {
    private String routeId;
    private List<RoutePoint> waypoints;
    private Double totalDistance; // km
    private Integer estimatedDuration; // minutes
    private String trafficCondition; // LIGHT, MODERATE, HEAVY
    private List<String> alternativeRoutes;
}