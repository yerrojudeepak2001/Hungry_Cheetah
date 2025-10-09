package com.foodapp.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherInfo {
    private String condition;
    private double temperature;
    private double windSpeed;
    private double precipitation;
    private double visibility;
    private double humidity;
    private String description;
}