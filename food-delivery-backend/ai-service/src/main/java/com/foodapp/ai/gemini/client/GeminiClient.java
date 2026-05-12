package com.foodapp.ai.gemini.client;

import com.foodapp.ai.gemini.config.GeminiApiProperties;
import com.foodapp.ai.gemini.dto.GeminiRequest;
import com.foodapp.ai.gemini.dto.GeminiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Service
public class GeminiClient {
    private static final Logger logger = LoggerFactory.getLogger(GeminiClient.class);
    
    private final WebClient webClient;
    private final GeminiApiProperties apiProperties;

    @Autowired
    public GeminiClient(GeminiApiProperties apiProperties) {
        this.apiProperties = apiProperties;
        this.webClient = WebClient.builder()
                .baseUrl(apiProperties.getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    public Mono<String> generateContent(String prompt) {
        logger.debug("Sending request to Gemini API with prompt: {}", prompt.substring(0, Math.min(prompt.length(), 100)) + "...");
        
        GeminiRequest request = new GeminiRequest(prompt);
        
        String url = String.format("/models/%s:generateContent?key=%s", 
                apiProperties.getModel(), 
                apiProperties.getKey());

        return webClient.post()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(GeminiResponse.class)
                .timeout(Duration.ofMillis(apiProperties.getTimeout()))
                .map(GeminiResponse::getText)
                .doOnSuccess(response -> logger.debug("Received response from Gemini API: {}", 
                    response != null ? response.substring(0, Math.min(response.length(), 100)) + "..." : "null"))
                .doOnError(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webError = (WebClientResponseException) error;
                        logger.error("Gemini API error - Status: {}, Response: {}", 
                                webError.getStatusCode(), webError.getResponseBodyAsString());
                    } else {
                        logger.error("Error calling Gemini API", error);
                    }
                })
                .onErrorReturn("Sorry, I'm unable to process your request at the moment. Please try again later.");
    }

    public Mono<String> getFoodRecommendations(String userPreferences, String dietaryRestrictions, String moodOrOccasion) {
        String prompt = String.format(
                "As a food recommendation expert, suggest 5 food items based on the following:\n" +
                "User Preferences: %s\n" +
                "Dietary Restrictions: %s\n" +
                "Mood/Occasion: %s\n\n" +
                "Please provide recommendations in this JSON format:\n" +
                "{\n" +
                "  \"recommendations\": [\n" +
                "    {\n" +
                "      \"name\": \"Food Name\",\n" +
                "      \"description\": \"Brief description\",\n" +
                "      \"category\": \"Category\",\n" +
                "      \"estimatedPrice\": \"$X-Y\",\n" +
                "      \"prepTime\": \"X minutes\",\n" +
                "      \"healthScore\": X,\n" +
                "      \"tags\": [\"tag1\", \"tag2\"]\n" +
                "    }\n" +
                "  ],\n" +
                "  \"explanation\": \"Brief explanation of why these foods were recommended\"\n" +
                "}",
                userPreferences, dietaryRestrictions, moodOrOccasion);

        return generateContent(prompt);
    }

    public Mono<String> getNutritionInfo(String foodItem) {
        String prompt = String.format(
                "Provide detailed nutrition information for: %s\n\n" +
                "Please respond in this JSON format:\n" +
                "{\n" +
                "  \"foodItem\": \"%s\",\n" +
                "  \"servingSize\": \"X grams/pieces\",\n" +
                "  \"calories\": X,\n" +
                "  \"nutrients\": {\n" +
                "    \"protein\": \"X g\",\n" +
                "    \"carbs\": \"X g\",\n" +
                "    \"fat\": \"X g\",\n" +
                "    \"fiber\": \"X g\",\n" +
                "    \"sugar\": \"X g\",\n" +
                "    \"sodium\": \"X mg\"\n" +
                "  },\n" +
                "  \"vitamins\": [\"Vitamin A\", \"Vitamin C\"],\n" +
                "  \"healthBenefits\": [\"benefit1\", \"benefit2\"],\n" +
                "  \"allergensWarning\": [\"allergen1\", \"allergen2\"]\n" +
                "}",
                foodItem, foodItem);

        return generateContent(prompt);
    }

    public Mono<String> generateRecipe(String dishName, String servings, String difficulty) {
        String prompt = String.format(
                "Generate a detailed recipe for: %s\n" +
                "Servings: %s\n" +
                "Difficulty Level: %s\n\n" +
                "Please respond in this JSON format:\n" +
                "{\n" +
                "  \"recipeName\": \"%s\",\n" +
                "  \"servings\": %s,\n" +
                "  \"difficulty\": \"%s\",\n" +
                "  \"prepTime\": \"X minutes\",\n" +
                "  \"cookTime\": \"X minutes\",\n" +
                "  \"totalTime\": \"X minutes\",\n" +
                "  \"ingredients\": [\n" +
                "    {\n" +
                "      \"item\": \"ingredient name\",\n" +
                "      \"amount\": \"X cups/pieces\",\n" +
                "      \"notes\": \"optional notes\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"instructions\": [\n" +
                "    \"Step 1 instructions\",\n" +
                "    \"Step 2 instructions\"\n" +
                "  ],\n" +
                "  \"tips\": [\"cooking tip 1\", \"cooking tip 2\"],\n" +
                "  \"nutritionHighlights\": \"Key nutrition benefits\"\n" +
                "}",
                dishName, servings, difficulty, dishName, servings, difficulty);

        return generateContent(prompt);
    }

    public Mono<String> analyzeMood(String userInput) {
        String prompt = String.format(
                "Analyze the following user input to understand their food mood and preferences: '%s'\n\n" +
                "Please respond in this JSON format:\n" +
                "{\n" +
                "  \"detectedMood\": \"mood description\",\n" +
                "  \"foodCravings\": [\"craving1\", \"craving2\"],\n" +
                "  \"recommendedCuisines\": [\"cuisine1\", \"cuisine2\"],\n" +
                "  \"mealType\": \"breakfast/lunch/dinner/snack\",\n" +
                "  \"preferenceIntensity\": \"mild/moderate/strong\",\n" +
                "  \"suggestedFlavors\": [\"sweet\", \"spicy\", \"savory\"],\n" +
                "  \"confidence\": 0.95\n" +
                "}",
                userInput);

        return generateContent(prompt);
    }
}