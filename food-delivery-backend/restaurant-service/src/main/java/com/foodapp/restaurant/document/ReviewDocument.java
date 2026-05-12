package com.foodapp.restaurant.document;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "reviews")
public class ReviewDocument {
    
    @Id
    private String id;
    
    @Indexed
    private Long restaurantId;
    
    @Indexed
    private Long userId;
    
    private String userName;
    private String userEmail;
    private String userProfilePicture;
    
    private int rating;  // 1-5 stars
    private String comment;
    private String title;
    
    // Detailed ratings (flexible schema advantage)
    private Map<String, Integer> detailedRatings;  // e.g., {"food": 5, "service": 4, "ambiance": 5, "value": 4}
    
    // Review metadata
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean verified;
    private boolean isEdited;
    
    // Engagement metrics
    private int helpfulCount;
    private int notHelpfulCount;
    private List<String> helpfulVoters;  // User IDs who found this helpful
    
    // Photos and media
    private List<String> photoUrls;
    private List<String> videoUrls;
    
    // Review response from restaurant
    private RestaurantResponse restaurantResponse;
    
    // Order information for verified reviews
    private Long orderId;
    private LocalDateTime orderDate;
    private List<String> itemsOrdered;
    
    // Moderation
    private String status;  // PENDING, APPROVED, REJECTED, FLAGGED
    private String moderationReason;
    private LocalDateTime moderatedAt;
    private String moderatedBy;
    
    // Tags for categorization
    private List<String> tags;  // e.g., ["vegetarian", "quick-service", "family-friendly"]
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RestaurantResponse {
        private String responseText;
        private LocalDateTime respondedAt;
        private String respondedBy;
        private String responderId;
    }
}
