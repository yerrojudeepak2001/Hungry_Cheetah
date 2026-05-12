package com.foodapp.ai.vision;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.Base64;

@Service
public class VisionRecognitionService {
    private final RestTemplate restTemplate;
    private static final String GEMINI_VISION_API = "https://api.gemini.com/v1/vision/analyze";
    private final String apiKey; // Inject from configuration

    public VisionRecognitionService(RestTemplate restTemplate, String apiKey) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
    }

    public Map<String, Object> analyzeFoodImage(MultipartFile image) throws Exception {
        // Convert image to base64
        String base64Image = Base64.getEncoder().encodeToString(image.getBytes());

        // Prepare request for Gemini Vision API
        Map<String, Object> request = new HashMap<>();
        request.put("image", base64Image);
        request.put("features", List.of("FOOD_DETECTION", "INGREDIENT_RECOGNITION"));

        // Call Gemini Vision API
        Map<String, Object> response = restTemplate.postForObject(
            GEMINI_VISION_API,
            request,
            Map.class
        );

        return processFoodRecognitionResults(response);
    }

    private Map<String, Object> processFoodRecognitionResults(Map<String, Object> visionResponse) {
        Map<String, Object> results = new HashMap<>();
        
        // Extract food items and ingredients
        List<String> detectedItems = (List<String>) visionResponse.get("detectedFoodItems");
        List<String> ingredients = (List<String>) visionResponse.get("ingredients");

        // Search for similar menu items
        results.put("detectedDish", detectedItems);
        results.put("ingredients", ingredients);
        results.put("similarMenuItems", findSimilarMenuItems(detectedItems, ingredients));

        return results;
    }

    private List<Map<String, Object>> findSimilarMenuItems(List<String> detectedItems, List<String> ingredients) {
        // TODO: Implement logic to find similar menu items based on detected food and ingredients
        return null;
    }
}