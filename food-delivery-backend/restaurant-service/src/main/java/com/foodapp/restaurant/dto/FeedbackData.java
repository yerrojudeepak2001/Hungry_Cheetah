package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FeedbackData {
    private Long feedbackId;
    private Long restaurantId;
    private Long customerId;
    private Integer rating;
    private String comment;
    private String feedbackType;
    private java.time.LocalDateTime createdAt;
}