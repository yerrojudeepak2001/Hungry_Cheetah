package com.foodapp.restaurant.dto.feedback;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RatingInfo {
    private Long restaurantId;
    private double averageRating;
    private int totalRatings;
    private List<Integer> ratingDistribution;
}