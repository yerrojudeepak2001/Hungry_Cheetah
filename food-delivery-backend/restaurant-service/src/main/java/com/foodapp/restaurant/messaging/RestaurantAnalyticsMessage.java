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
public class RestaurantAnalyticsMessage implements Serializable {
    private String eventType; // ORDER_RECEIVED, MENU_VIEWED, REVIEW_RECEIVED, STATUS_CHANGED, etc.
    private String restaurantId;
    private String customerId; // optional, for customer-related events
    private Map<String, Object> eventData;
    private long timestamp;
}