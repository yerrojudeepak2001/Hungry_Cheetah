package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class KitchenStatus {
    private Long restaurantId;
    private String overallStatus; // OPEN, BUSY, CLOSED
    private Integer activeOrders;
    private Integer avgPrepTime;
    private Integer capacity;
    private Double efficiency;
    private java.time.LocalDateTime lastUpdated;
}