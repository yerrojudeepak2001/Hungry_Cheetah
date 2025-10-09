package com.foodapp.delivery.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.delivery.dto.WeatherInfo;

@FeignClient(name = "WEATHER-SERVICE", fallback = WeatherClientFallback.class)
public interface WeatherClient {
    @GetMapping("/api/weather/current")
    WeatherInfo getCurrentWeather(@RequestParam double latitude, 
                                @RequestParam double longitude);
    
    @GetMapping("/api/weather/forecast")
    List<WeatherInfo> getWeatherForecast(@RequestParam double latitude, 
                                        @RequestParam double longitude);
    
    @GetMapping("/api/weather/alerts")
    List<WeatherAlert> getActiveWeatherAlerts(@RequestParam double latitude, 
                                            @RequestParam double longitude);
    
    @GetMapping("/api/weather/delivery-impact")
    DeliveryImpact getWeatherImpact(@RequestParam double latitude, 
                                   @RequestParam double longitude);
}