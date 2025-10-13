package com.foodapp.user.client;

import com.foodapp.user.dto.RestaurantResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class RestaurantClientFallback implements RestaurantClient {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantClientFallback.class);

    @Override
    public List<RestaurantResponse> getUserFavoriteRestaurants(String userId) {
        logger.warn("Fallback: Could not fetch favorite restaurants for user {}", userId);
        return Collections.emptyList();
    }

    @Override
    public List<RestaurantResponse> getUserRecentRestaurants(String userId) {
        logger.warn("Fallback: Could not fetch recent restaurants for user {}", userId);
        return Collections.emptyList();
    }

    @Override
    public void addRestaurantToFavorites(String userId, String restaurantId) {
        logger.warn("Fallback: Could not add restaurant {} to favorites for user {}", restaurantId, userId);
        // fallback does nothing
    }

    @Override
    public void removeRestaurantFromFavorites(String userId, String restaurantId) {
        logger.warn("Fallback: Could not remove restaurant {} from favorites for user {}", restaurantId, userId);
        // fallback does nothing
    }
}
