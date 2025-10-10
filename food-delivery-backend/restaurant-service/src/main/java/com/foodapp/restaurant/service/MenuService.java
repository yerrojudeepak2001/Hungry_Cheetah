package com.foodapp.restaurant.service;

import com.foodapp.restaurant.model.MenuItem;
import com.foodapp.restaurant.model.SpecialMenu;
import java.util.List;

public interface MenuService {
    MenuItem addMenuItem(Long restaurantId, MenuItem item);
    MenuItem updateMenuItem(Long restaurantId, Long itemId, MenuItem item);
    void deleteMenuItem(Long restaurantId, Long itemId);
    void removeMenuItem(Long restaurantId, Long itemId);
    List<MenuItem> getRestaurantMenu(Long restaurantId);
    SpecialMenu createSpecialMenu(Long restaurantId, SpecialMenu specialMenu);
    SpecialMenu addSpecialMenu(Long restaurantId, SpecialMenu specialMenu);
    List<SpecialMenu> getActiveSpecialMenus(Long restaurantId);
    List<SpecialMenu> getSpecialMenus(Long restaurantId);
    void updateMenuItemAvailability(Long restaurantId, Long itemId, boolean available);
}