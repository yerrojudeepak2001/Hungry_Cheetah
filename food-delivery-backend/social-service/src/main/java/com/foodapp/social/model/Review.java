package com.foodapp.social.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "reviews")
public class Review {
    @Id
    private String id;
    private Long userId;
    private Long restaurantId;
    private Long orderId;
    private Double rating;
    private String review;
    private LocalDateTime createdAt;
    
    // Media
    private List<String> photos;
    private List<String> videos;
    private Map<String, String> mediaMetadata;
    
    // Category Ratings
    private Double foodRating;
    private Double serviceRating;
    private Double deliveryRating;
    private Double valueForMoneyRating;
    
    // Review Details
    private List<String> tags;
    private List<String> highlightedDishes;
    private String recommendationType; // RECOMMENDED, NOT_RECOMMENDED, NEUTRAL
    private List<String> pros;
    private List<String> cons;
    
    // Engagement
    private Integer likes;
    private Integer helpful;
    private Integer notHelpful;
    private List<Comment> comments;
    private Boolean isVerifiedOrder;
    
    // Restaurant Response
    private RestaurantResponse restaurantResponse;
    private Boolean hasRestaurantResponse;
    
    // Moderation
    private String moderationStatus;
    private LocalDateTime moderatedAt;
    private String moderationNote;
    
    @Data
    public static class Comment {
        private String id;
        private Long userId;
        private String comment;
        private LocalDateTime createdAt;
        private Integer likes;
        private Boolean isEdited;
        private String moderationStatus;
    }
    
    @Data
    public static class RestaurantResponse {
        private String response;
        private String responderName;
        private String responderRole;
        private LocalDateTime respondedAt;
        private Boolean isEdited;
    }
}