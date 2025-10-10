package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuData {
    private Long restaurantId;
    private List<MenuItemDTO> items;
    private LocalDateTime lastUpdated;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemDTO {
    private Long itemId;
    private String name;
    private String category;
    private double price;
    private boolean available;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuItemAvailability {
    private Long itemId;
    private boolean available;
    private String reason;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MenuSyncRequest {
    private Long restaurantId;
    private List<MenuItemDTO> items;
    private String syncType;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class POSMenuSync {
    private Long restaurantId;
    private List<MenuItemDTO> items;
    private String syncType;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class POSOrderData {
    private Long orderId;
    private List<OrderItemDTO> items;
    private String status;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemDTO {
    private Long itemId;
    private int quantity;
    private double price;
    private String notes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class POSIntegrationStatus {
    private Long restaurantId;
    private String status;
    private LocalDateTime lastSyncTime;
    private String errorMessage;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationRequest {
    private Long userId;
    private List<String> preferences;
    private String location;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationResponse {
    private List<RestaurantRecommendation> recommendations;
    private String explanation;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantRecommendation {
    private Long restaurantId;
    private String name;
    private double score;
    private List<String> reasons;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendationFeedback {
    private Long userId;
    private Long restaurantId;
    private boolean helpful;
    private String comment;
}