package com.foodapp.restaurant.dto;

import lombok.Data;
import lombok.Builder;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DeliveryTimeResponse {
    private Long restaurantId;
    private Integer estimatedDeliveryMinutes;
    private String timeSlot;
    private Boolean isAvailable;
    private String message;
}