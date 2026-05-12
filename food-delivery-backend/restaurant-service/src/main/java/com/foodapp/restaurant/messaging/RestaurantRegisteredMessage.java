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
public class RestaurantRegisteredMessage implements Serializable {
    private String restaurantId;
    private String restaurantName;
    private String ownerName;
    private String ownerEmail;
    private String ownerPhone;
    private String cuisine;
    private String address;
    private String city;
    private String state;
    private String zipCode;
    private Map<String, Object> metadata;
    private long timestamp;
}