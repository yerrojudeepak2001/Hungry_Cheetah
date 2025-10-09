package com.foodapp.order.dto;

import lombok.Data;

@Data
public class RestaurantResponse {
    private Long id;
    private String name;
    private String address;
    private String cuisine;
    private boolean active;
    private double rating;
    private int preparationTime;
}