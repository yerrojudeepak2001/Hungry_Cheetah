package com.foodapp.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantDeliveryInfo {
    private String restaurantId;
    private int estimatedDeliveryTime; // in minutes
    private double deliveryFee;
}
