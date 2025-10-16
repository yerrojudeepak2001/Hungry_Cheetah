package com.foodapp.pricing.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.pricing.dto.WeatherImpact;
import com.foodapp.pricing.dto.SevereCondition;
import com.foodapp.pricing.client.fallback.WeatherClientFallback;
import java.util.List;

@FeignClient(name = "WEATHER-SERVICE", fallback = WeatherClientFallback.class)
public interface WeatherClient {
    @GetMapping("/api/weather/location/{locationId}/impact")
    WeatherImpact getWeatherPricingImpact(@PathVariable("locationId") String locationId);
    
    @GetMapping("/api/weather/forecast/pricing")
    List<WeatherImpact> getForecastPricingImpact(@RequestParam String locationId,
                                                @RequestParam int hours);
    
    @GetMapping("/api/weather/severe-conditions")
    List<SevereCondition> getSevereConditions(@RequestParam String region);
    
    @GetMapping("/api/weather/impact")
    WeatherImpact getWeatherImpact(@RequestParam String area);
}