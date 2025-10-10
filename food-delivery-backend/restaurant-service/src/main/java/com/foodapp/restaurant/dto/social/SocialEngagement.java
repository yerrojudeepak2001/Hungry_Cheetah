package com.foodapp.restaurant.dto.social;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialEngagement {
    private Long id;
    private Long restaurantId;
    private Integer followersCount;
    private Double averageRating;
    private Integer totalReviews;
    private Integer totalPhotos;
    private Map<String, Integer> socialMetrics;
    private Double engagementRate;
}