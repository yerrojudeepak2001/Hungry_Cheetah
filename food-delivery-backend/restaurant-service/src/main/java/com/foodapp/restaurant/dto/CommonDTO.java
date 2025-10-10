package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeliveryTimeResponse {
    private Long restaurantId;
    private Long orderId;
    private LocalDateTime estimatedDeliveryTime;
    private double distance;
    private String deliveryNotes;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private Long restaurantId;
    private String orderStatus;
    private LocalDateTime orderTime;
    private String orderDetails;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StockLevel {
    private Long itemId;
    private int quantity;
    private String status;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryUpdate {
    private Long itemId;
    private int quantityChange;
    private String reason;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryAlert {
    private Long itemId;
    private String alertType;
    private String message;
    private LocalDateTime timestamp;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventoryForecast {
    private Long itemId;
    private int predictedDemand;
    private int suggestedStockLevel;
    private LocalDateTime forecastDate;
}

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