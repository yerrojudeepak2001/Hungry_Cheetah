package com.foodapp.search.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "search_index")
public class SearchIndex {
    @Id
    private String id;
    private String itemType; // RESTAURANT, DISH, CUISINE
    private String name;
    private String description;
    private List<String> keywords;
    private List<String> tags;
    
    // Search Relevance
    private Double popularityScore;
    private Double ratingScore;
    private Double trendingScore;
    private Map<String, Double> categoryScores;
    
    // Contextual Data
    private List<String> dietaryTags;
    private String priceRange;
    private List<String> mealTimes;
    private List<String> occasions;
    
    // Location Data
    private String area;
    private String city;
    private List<String> landmarks;
    private Double latitude;
    private Double longitude;
    
    // Dynamic Attributes
    private Boolean isAvailable;
    private Boolean hasOffer;
    private Integer preparationTime;
    private Double deliveryTime;
    
    // Metadata
    private Map<String, Object> attributes;
    private List<String> relatedItems;
    private List<String> alternatives;
}