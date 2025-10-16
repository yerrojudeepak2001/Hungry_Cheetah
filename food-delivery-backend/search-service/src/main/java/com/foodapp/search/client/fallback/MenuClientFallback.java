package com.foodapp.search.client.fallback;

import com.foodapp.search.client.MenuClient;
import com.foodapp.search.dto.MenuIndexData;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;
import java.time.LocalDateTime;

@Component
public class MenuClientFallback implements MenuClient {

    @Override
    public List<MenuIndexData> getMenuUpdates(LocalDateTime since) {
        // Return empty list when menu service is unavailable
        return new ArrayList<>();
    }

    @Override
    public MenuIndexData getRestaurantMenu(String restaurantId) {
        // Return null or empty menu when menu service is unavailable
        return new MenuIndexData();
    }

    @Override
    public List<String> getAllCategories() {
        // Return some default categories when menu service is unavailable
        List<String> defaultCategories = new ArrayList<>();
        defaultCategories.add("APPETIZER");
        defaultCategories.add("MAIN_COURSE");
        defaultCategories.add("DESSERT");
        defaultCategories.add("BEVERAGE");
        return defaultCategories;
    }

    @Override
    public List<MenuIndexData> getTrendingItems() {
        // Return empty list when menu service is unavailable
        return new ArrayList<>();
    }
}