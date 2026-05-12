package com.foodapp.ai.recommendation.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "taste_profiles")
public class TasteProfile {
    @Id
    private String id;
    private Long userId;
    private List<String> preferredCuisines;
    private List<String> dietaryRestrictions;
    private List<String> allergies;
    private List<String> favoriteIngredients;
    private Double spicePreference; // Scale of 1-5
    private List<String> previousOrders;
}