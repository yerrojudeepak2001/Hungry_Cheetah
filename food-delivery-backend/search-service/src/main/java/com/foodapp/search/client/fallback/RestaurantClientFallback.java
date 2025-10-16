package com.foodapp.search.client.fallback;

import com.foodapp.search.client.RestaurantClient;
import com.foodapp.search.dto.RestaurantSearchData;
import com.foodapp.search.dto.MenuItemSearchData;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.ArrayList;

@Component
public class RestaurantClientFallback implements RestaurantClient {

    @Override
    public List<RestaurantSearchData> getRestaurantSearchData() {
        // Return empty list when restaurant service is unavailable
        return new ArrayList<>();
    }

    @Override
    public List<MenuItemSearchData> getMenuItems(String restaurantId) {
        // Return empty list when restaurant service is unavailable
        return new ArrayList<>();
    }

    @Override
    public List<String> getAllCategories() {
        // Return some default categories when restaurant service is unavailable
        List<String> defaultCategories = new ArrayList<>();
        defaultCategories.add("APPETIZER");
        defaultCategories.add("MAIN_COURSE");
        defaultCategories.add("DESSERT");
        defaultCategories.add("BEVERAGE");
        return defaultCategories;
    }
}