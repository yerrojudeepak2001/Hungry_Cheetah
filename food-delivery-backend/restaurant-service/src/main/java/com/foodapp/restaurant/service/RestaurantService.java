package com.foodapp.restaurant.service;

import com.foodapp.restaurant.model.Restaurant;
import java.util.List;

public interface RestaurantService {
    Restaurant registerRestaurant(Restaurant restaurant);
    Restaurant getRestaurantDetails(Long restaurantId);
    Restaurant updateRestaurant(Long restaurantId, Restaurant restaurant);
    void deleteRestaurant(Long restaurantId);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> searchRestaurants(String searchTerm);
    List<Restaurant> getRestaurantsByCuisine(String cuisine);
    List<Restaurant> getNearbyRestaurants(double latitude, double longitude, double radius);
}