package com.foodapp.user.client;

import com.foodapp.user.dto.RestaurantResponse;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class RestaurantClientFallback implements RestaurantClient {

    @Override
    public List<RestaurantResponse> getUserFavoriteRestaurants(String userId) {
        return Collections.emptyList();
    }

    @Override
    public List<RestaurantResponse> getUserRecentRestaurants(String userId) {
        return Collections.emptyList();
    }
}