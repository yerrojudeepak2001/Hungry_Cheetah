package com.foodapp.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantLocation {
    private String restaurantId;
    private String address;
    private double latitude;
    private double longitude;
}
