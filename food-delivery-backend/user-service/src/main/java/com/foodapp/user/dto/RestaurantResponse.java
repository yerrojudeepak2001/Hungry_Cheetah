package com.foodapp.user.dto;

import lombok.Data;

@Data
public class RestaurantResponse {
    private String restaurantId;
    private String name;
    private String cuisine;
    private Double rating;
    private String address;
}