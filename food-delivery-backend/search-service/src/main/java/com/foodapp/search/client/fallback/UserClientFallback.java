package com.foodapp.search.client.fallback;

import com.foodapp.search.client.UserClient;
import com.foodapp.search.dto.UserPreferences;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class UserClientFallback implements UserClient {

    @Override
    public UserPreferences getUserPreferences(String userId) {
        // Return default preferences when user service is unavailable
        UserPreferences defaultPreferences = new UserPreferences();
        // Set some default values
        return defaultPreferences;
    }

    @Override
    public List<String> getUserDietaryRestrictions(String userId) {
        // Return empty list when user service is unavailable
        return new ArrayList<>();
    }

    @Override
    public List<String> getUserFavoriteCuisines(String userId) {
        // Return empty list when user service is unavailable
        return new ArrayList<>();
    }
}