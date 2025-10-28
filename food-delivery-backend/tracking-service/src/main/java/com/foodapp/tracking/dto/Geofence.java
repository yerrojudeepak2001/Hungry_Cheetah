package com.foodapp.tracking.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Geofence {
    private String id;
    private double centerLatitude;
    private double centerLongitude;
    private double radius;
    private String name;
    private boolean active;
}