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
public class RestaurantStatusChangedMessage implements Serializable {
    private String restaurantId;
    private String restaurantName;
    private String ownerEmail;
    private String oldStatus;
    private String newStatus; // OPEN, CLOSED, TEMPORARILY_CLOSED, SUSPENDED
    private String reason;
    private String changedBy; // OWNER, ADMIN, SYSTEM
    private Map<String, Object> metadata;
    private long timestamp;
}