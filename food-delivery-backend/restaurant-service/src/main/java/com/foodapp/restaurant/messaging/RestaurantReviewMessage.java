package com.foodapp.restaurant.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantReviewMessage implements Serializable {
    private String reviewId;
    private String restaurantId;
    private String restaurantName;
    private String ownerEmail;
    private String customerId;
    private String customerName;
    private int rating;
    private String reviewText;
    private String orderId;
    private Map<String, Object> metadata;
    private long timestamp;
}