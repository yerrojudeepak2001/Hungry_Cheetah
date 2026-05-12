package com.foodapp.ai.recommendation;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;

@Service
public class WeatherIntegrationService {
    private final RestTemplate restTemplate;
    private final String WEATHER_API_KEY = "your-api-key"; // TODO: Move to config
    private final String WEATHER_API_URL = "https://api.openweathermap.org/data/2.5/weather";

    public WeatherIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Map<String, Object> getWeatherBasedRecommendations(String latitude, String longitude) {
        String url = String.format("%s?lat=%s&lon=%s&appid=%s", 
            WEATHER_API_URL, latitude, longitude, WEATHER_API_KEY);
        
        Map<String, Object> weatherData = restTemplate.getForObject(url, Map.class);
        return processWeatherForFoodRecommendations(weatherData);
    }

    private Map<String, Object> processWeatherForFoodRecommendations(Map<String, Object> weatherData) {
        Map<String, Object> recommendations = new HashMap<>();
        // Example logic for weather-based recommendations
        Map<String, Object> main = (Map<String, Object>) weatherData.get("main");
        double temp = (double) main.get("temp");
        
        if (temp < 283.15) { // Below 10°C
            recommendations.put("type", "hot");
            recommendations.put("suggestions", new String[]{"Hot Soup", "Hot Coffee", "Warm Pasta"});
        } else if (temp > 303.15) { // Above 30°C
            recommendations.put("type", "cold");
            recommendations.put("suggestions", new String[]{"Ice Cream", "Cold Drinks", "Salads"});
        }
        
        return recommendations;
    }
}