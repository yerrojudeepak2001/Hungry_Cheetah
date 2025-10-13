package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RecommendationRequest {
    private Long customerId;
    private Long restaurantId;
    private String requestType;
    private java.util.Map<String, Object> preferences;
    private java.time.LocalDateTime requestTime;
}