package com.foodapp.restaurant.service.impl;

import com.foodapp.restaurant.repository.ARMenuRepository;
import com.foodapp.restaurant.repository.MenuItemRepository;
import com.foodapp.restaurant.repository.RestaurantRepository;
import com.foodapp.restaurant.repository.VirtualTourRepository;
import com.foodapp.restaurant.service.RestaurantService;
import com.foodapp.restaurant.model.MenuItem;
import com.foodapp.restaurant.model.Restaurant;
import com.foodapp.restaurant.model.ARMenu;
import com.foodapp.restaurant.model.VirtualTour;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final ARMenuRepository arMenuRepository;
    private final VirtualTourRepository virtualTourRepository;
    private final MenuItemRepository menuItemRepository;

    @Override
    public Restaurant registerRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }

    @Override
    public Restaurant getRestaurantDetails(Long restaurantId) {
        return restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
    }

    @Override
    public Restaurant updateRestaurant(Long restaurantId, Restaurant restaurant) {
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        restaurant.setId(restaurantId);
        return restaurantRepository.save(restaurant);
    }

    @Override
    public void deleteRestaurant(Long restaurantId) {
        restaurantRepository.deleteById(restaurantId);
    }

    @Override
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }

    @Override
    public List<Restaurant> searchRestaurants(String searchTerm) {
        return restaurantRepository.findByNameContainingIgnoreCaseOrCuisineContainingIgnoreCase(searchTerm, searchTerm);
    }

    @Override
    public List<Restaurant> getRestaurantsByCuisine(String cuisine) {
        return restaurantRepository.findByCuisineContainingIgnoreCase(cuisine);
    }

    @Override
    public List<Restaurant> getNearbyRestaurants(double latitude, double longitude, double radius) {
        // In a real application, this would use spatial queries with a database that supports geospatial functions
        // For now, we'll implement a simple version using the Haversine formula to calculate distance
        List<Restaurant> allRestaurants = restaurantRepository.findAll();
        
        List<Restaurant> nearbyRestaurants = new ArrayList<>();
        for (Restaurant restaurant : allRestaurants) {
            // This would require location data in the Restaurant entity
            // For demonstration purposes, let's assume we would check the distance here
            
            // Example of how it could work if we had latitude and longitude in the Restaurant entity:
            // double distance = calculateDistance(latitude, longitude, restaurant.getLatitude(), restaurant.getLongitude());
            // if (distance <= radius) {
            //     nearbyRestaurants.add(restaurant);
            // }
        }
        
        return nearbyRestaurants;
    }

    @Override
    public Map<String, Object> getOrderAnalytics(Long restaurantId, String period) {
        // This would typically involve complex queries to the order database
        // For demonstration purposes, we'll return mock analytics
        Map<String, Object> analytics = new HashMap<>();
        
        // Verify that the restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        // Example analytics data
        analytics.put("totalOrders", 150);
        analytics.put("totalRevenue", 3250.75);
        analytics.put("averageOrderValue", 21.67);
        analytics.put("topSellingCategories", Arrays.asList("Pizza", "Pasta", "Salads"));
        analytics.put("period", period);
        
        return analytics;
    }

    @Override
    public List<Map<String, Object>> getPopularItems(Long restaurantId, String period) {
        // This would typically involve queries to the order database to find the most ordered items
        // For demonstration purposes, we'll return mock popular items
        
        // Verify that the restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        List<Map<String, Object>> popularItems = new ArrayList<>();
        
        // Example popular items
        Map<String, Object> item1 = new HashMap<>();
        item1.put("itemId", 101);
        item1.put("itemName", "Pepperoni Pizza");
        item1.put("orderCount", 45);
        item1.put("revenue", 675.50);
        popularItems.add(item1);
        
        Map<String, Object> item2 = new HashMap<>();
        item2.put("itemId", 203);
        item2.put("itemName", "Caesar Salad");
        item2.put("orderCount", 32);
        item2.put("revenue", 287.80);
        popularItems.add(item2);
        
        Map<String, Object> item3 = new HashMap<>();
        item3.put("itemId", 155);
        item3.put("itemName", "Spaghetti Carbonara");
        item3.put("orderCount", 28);
        item3.put("revenue", 336.00);
        popularItems.add(item3);
        
        return popularItems;
    }

    @Override
    public ARMenu uploadARMenu(Long restaurantId, ARMenu arMenu) {
        // Verify restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
                
        // Check if an AR menu already exists for this restaurant
        Optional<ARMenu> existingMenu = arMenuRepository.findByRestaurantId(restaurantId);
        
        if (existingMenu.isPresent()) {
            // Update existing AR menu
            ARMenu updatedMenu = existingMenu.get();
            updatedMenu.setModelUrl(arMenu.getModelUrl());
            updatedMenu.setPreviewImageUrl(arMenu.getPreviewImageUrl());
            updatedMenu.setDescription(arMenu.getDescription());
            updatedMenu.setActive(arMenu.isActive());
            return arMenuRepository.save(updatedMenu);
        } else {
            // Create new AR menu
            arMenu.setRestaurant(restaurant);
            return arMenuRepository.save(arMenu);
        }
    }

    @Override
    public ARMenu getARMenu(Long restaurantId) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        return arMenuRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new RuntimeException("AR Menu not found for restaurant id: " + restaurantId));
    }

    @Override
    public VirtualTour uploadVirtualTour(Long restaurantId, VirtualTour virtualTour) {
        // Verify restaurant exists
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
                .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
                
        // Check if a virtual tour already exists for this restaurant
        Optional<VirtualTour> existingTour = virtualTourRepository.findByRestaurantId(restaurantId);
        
        if (existingTour.isPresent()) {
            // Update existing virtual tour
            VirtualTour updatedTour = existingTour.get();
            updatedTour.setTourUrl(virtualTour.getTourUrl());
            updatedTour.setThumbnailUrl(virtualTour.getThumbnailUrl());
            updatedTour.setDescription(virtualTour.getDescription());
            updatedTour.setActive(virtualTour.isActive());
            return virtualTourRepository.save(updatedTour);
        } else {
            // Create new virtual tour
            virtualTour.setRestaurant(restaurant);
            virtualTour.setCreatedAt(LocalDateTime.now());
            return virtualTourRepository.save(virtualTour);
        }
    }

    @Override
    public VirtualTour getVirtualTour(Long restaurantId) {
        // Verify restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        return virtualTourRepository.findByRestaurantId(restaurantId)
                .orElseThrow(() -> new RuntimeException("Virtual Tour not found for restaurant id: " + restaurantId));
    }
}