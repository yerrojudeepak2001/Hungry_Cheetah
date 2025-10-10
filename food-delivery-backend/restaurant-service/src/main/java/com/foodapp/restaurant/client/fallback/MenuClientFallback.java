package com.foodapp.restaurant.client.fallback;

import com.foodapp.restaurant.client.MenuClient;
import com.foodapp.restaurant.dto.MenuData;
import com.foodapp.restaurant.dto.MenuItemAvailability;
import com.foodapp.restaurant.dto.MenuSyncRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Collections;

@Component
public class MenuClientFallback implements MenuClient {

    @Override
    public MenuData getRestaurantMenu(String restaurantId) {
        return MenuData.builder()
                .menuName("Default Menu")
                .items(Collections.emptyList())
                .isActive(false)
                .build();
    }

    @Override
    public void updateItemAvailability(String itemId, MenuItemAvailability availability) {
        // Fallback: Do nothing
    }

    @Override
    public List<MenuData> getDailySpecials(String restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public void syncMenuUpdates(MenuSyncRequest syncRequest) {
        // Fallback: Do nothing
    }
}