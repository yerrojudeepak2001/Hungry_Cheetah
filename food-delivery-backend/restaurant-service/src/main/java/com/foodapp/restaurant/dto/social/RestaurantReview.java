package com.foodapp.restaurant.dto.social;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantReview {
    private Long id;
    private Long restaurantId;
    private Long userId;
    private Integer rating;
    private String comment;
    private LocalDateTime reviewDate;
    private String status;
    private Integer helpfulVotes;
    private Boolean isVerified;
}