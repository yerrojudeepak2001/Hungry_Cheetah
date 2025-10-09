package com.foodapp.ai.emotion;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Map;
import java.util.HashMap;
import java.util.List;

@Service
public class EmotionAnalysisService {
    private final RestTemplate restTemplate;
    private static final String GEMINI_API_ENDPOINT = "https://api.gemini.com/v1/text/analyze";
    private final String apiKey; // Inject from configuration

    public EmotionAnalysisService(RestTemplate restTemplate, String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public Map<String, Object> analyzeMoodForFoodRecommendation(String userInput) {
        // Analyze text for emotional context
        Map<String, Object> request = new HashMap<>();
        request.put("text", userInput);
        request.put("features", List.of("emotion", "sentiment"));

        Map<String, Object> response = restTemplate.postForObject(
            GEMINI_API_ENDPOINT,
            request,
            Map.class
        );

        return processEmotionForFoodSuggestions(response);
    }

    private Map<String, Object> processEmotionForFoodSuggestions(Map<String, Object> emotionData) {
        Map<String, Object> foodSuggestions = new HashMap<>();
        
        // Example emotion-based food mapping
        String dominantEmotion = (String) emotionData.get("dominant_emotion");
        switch (dominantEmotion.toLowerCase()) {
            case "happy":
                foodSuggestions.put("suggestions", 
                    List.of("Celebratory desserts", "Colorful salads", "Fresh smoothies"));
                break;
            case "sad":
                foodSuggestions.put("suggestions", 
                    List.of("Comfort food", "Warm soup", "Chocolate desserts"));
                break;
            case "stressed":
                foodSuggestions.put("suggestions", 
                    List.of("Calming herbal teas", "Healthy snacks", "Light meals"));
                break;
            default:
                foodSuggestions.put("suggestions", 
                    List.of("Balanced meals", "Fresh fruits", "Healthy options"));
        }
        
        return foodSuggestions;
    }
}