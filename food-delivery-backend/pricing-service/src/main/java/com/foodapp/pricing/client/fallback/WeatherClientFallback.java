package com.foodapp.pricing.client.fallback;

import com.foodapp.pricing.client.WeatherClient;
import com.foodapp.pricing.dto.WeatherImpact;
import com.foodapp.pricing.dto.SevereCondition;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class WeatherClientFallback implements WeatherClient {

    @Override
    public WeatherImpact getWeatherPricingImpact(String locationId) {
        // Return neutral weather impact when weather service is unavailable
        WeatherImpact defaultImpact = new WeatherImpact();
        defaultImpact.setLocation(locationId);
        defaultImpact.setCondition("CLEAR");
        defaultImpact.setSeverity("LOW");
        defaultImpact.setTemperatureMultiplier(1.0);
        defaultImpact.setDemandImpact(1.0);
        defaultImpact.setDescription("Weather service unavailable - using default values");
        return defaultImpact;
    }

    @Override
    public List<WeatherImpact> getForecastPricingImpact(String locationId, int hours) {
        // Return empty list when weather service is unavailable
        return new ArrayList<>();
    }

    @Override
    public List<SevereCondition> getSevereConditions(String region) {
        // Return empty list when weather service is unavailable
        return new ArrayList<>();
    }
    
    @Override
    public WeatherImpact getWeatherImpact(String area) {
        // Return neutral weather impact when weather service is unavailable
        WeatherImpact defaultImpact = new WeatherImpact();
        defaultImpact.setLocation(area);
        defaultImpact.setCondition("CLEAR");
        defaultImpact.setSeverity("LOW");
        defaultImpact.setTemperatureMultiplier(1.0);
        defaultImpact.setDemandImpact(1.0);
        defaultImpact.setDescription("Weather service unavailable - using default values");
        return defaultImpact;
    }
}