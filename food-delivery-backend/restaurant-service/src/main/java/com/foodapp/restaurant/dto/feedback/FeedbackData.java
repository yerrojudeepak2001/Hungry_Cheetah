package com.foodapp.restaurant.dto.feedback;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackData {
    private Long restaurantId;
    private Long userId;
    private int rating;
    private String comment;
    private LocalDateTime timestamp;
}