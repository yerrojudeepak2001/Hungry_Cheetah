package com.foodapp.delivery.client;

import org.springframework.stereotype.Component;
import com.foodapp.delivery.dto.WeatherInfo;

@Component
public class WeatherClientFallback implements WeatherClient {
    @Override
    public WeatherInfo getCurrentWeather(double latitude, double longitude) {
        return WeatherInfo.builder()
                .condition("UNKNOWN")
                .temperature(0.0)
                .windSpeed(0.0)
                .precipitation(0.0)
                .build();
    }
    
    @Override
    public List<WeatherInfo> getWeatherForecast(double latitude, double longitude) {
        return Collections.emptyList();
    }
    
    @Override
    public List<WeatherAlert> getActiveWeatherAlerts(double latitude, double longitude) {
        return Collections.emptyList();
    }
    
    @Override
    public DeliveryImpact getWeatherImpact(double latitude, double longitude) {
        return DeliveryImpact.builder()
                .severity("UNKNOWN")
                .estimatedDelay(0)
                .recommendations(Collections.emptyList())
                .build();
    }
}