package com.foodapp.driver.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoutePoint {
    private Double latitude;
    private Double longitude;
    private String address;
    private String pointType; // PICKUP, DELIVERY, WAYPOINT
    private Integer sequenceNumber;
    private String instructions;
}