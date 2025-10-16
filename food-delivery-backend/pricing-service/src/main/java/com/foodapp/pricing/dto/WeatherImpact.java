package com.foodapp.pricing.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WeatherImpact {
    private String location;
    private String condition; // RAIN, SNOW, STORM, CLEAR
    private String severity; // HIGH, MEDIUM, LOW
    private Double temperatureMultiplier;
    private Double demandImpact;
    private String description;
}