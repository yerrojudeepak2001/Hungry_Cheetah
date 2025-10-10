package com.foodapp.restaurant.service;

import com.foodapp.restaurant.model.Restaurant;
import com.foodapp.restaurant.model.ARMenu;
import com.foodapp.restaurant.model.VirtualTour;
import java.util.List;
import java.util.Map;

public interface RestaurantService {
    Restaurant registerRestaurant(Restaurant restaurant);
    Restaurant getRestaurantDetails(Long restaurantId);
    Restaurant updateRestaurant(Long restaurantId, Restaurant restaurant);
    void deleteRestaurant(Long restaurantId);
    List<Restaurant> getAllRestaurants();
    List<Restaurant> searchRestaurants(String searchTerm);
    List<Restaurant> getRestaurantsByCuisine(String cuisine);
    List<Restaurant> getNearbyRestaurants(double latitude, double longitude, double radius);
    Map<String, Object> getOrderAnalytics(Long restaurantId, String period);
    List<Map<String, Object>> getPopularItems(Long restaurantId, String period);
    ARMenu uploadARMenu(Long restaurantId, ARMenu arMenu);
    ARMenu getARMenu(Long restaurantId);
    VirtualTour uploadVirtualTour(Long restaurantId, VirtualTour virtualTour);
    VirtualTour getVirtualTour(Long restaurantId);
}