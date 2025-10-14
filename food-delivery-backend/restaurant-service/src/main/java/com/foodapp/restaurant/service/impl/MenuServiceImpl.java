package com.foodapp.restaurant.service.impl;

import com.foodapp.restaurant.repository.MenuItemRepository;
import com.foodapp.restaurant.repository.RestaurantRepository;
import com.foodapp.restaurant.repository.SpecialMenuRepository;
import com.foodapp.restaurant.service.MenuService;
import com.foodapp.restaurant.model.MenuItem;
import com.foodapp.restaurant.model.Restaurant;
import com.foodapp.restaurant.model.SpecialMenu;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MenuServiceImpl implements MenuService {
    
    private final RestaurantRepository restaurantRepository;
    private final MenuItemRepository menuItemRepository;
    private final SpecialMenuRepository specialMenuRepository;

    @Override
    public MenuItem addMenuItem(Long restaurantId, MenuItem item) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        
        item.setRestaurant(restaurant);
        return menuItemRepository.save(item);
    }

    @Override
    public MenuItem updateMenuItem(Long restaurantId, Long itemId, MenuItem item) {
        // Verify the menu item exists and belongs to the restaurant
        MenuItem existingItem = menuItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + itemId));
            
        if (!existingItem.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Menu item does not belong to restaurant with id: " + restaurantId);
        }
        
        // Update the menu item
        item.setId(itemId);
        item.setRestaurant(existingItem.getRestaurant());
        return menuItemRepository.save(item);
    }

    @Override
    public void deleteMenuItem(Long restaurantId, Long itemId) {
        // Verify the menu item exists and belongs to the restaurant
        MenuItem existingItem = menuItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + itemId));
            
        if (!existingItem.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Menu item does not belong to restaurant with id: " + restaurantId);
        }
        
        menuItemRepository.deleteById(itemId);
    }

    @Override
    public void removeMenuItem(Long restaurantId, Long itemId) {
        // This is essentially the same as deleteMenuItem in this implementation
        deleteMenuItem(restaurantId, itemId);
    }

    @Override
    public List<MenuItem> getRestaurantMenu(Long restaurantId) {
        // Verify the restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        return menuItemRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public SpecialMenu createSpecialMenu(Long restaurantId, SpecialMenu specialMenu) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId)
            .orElseThrow(() -> new RuntimeException("Restaurant not found with id: " + restaurantId));
        
        specialMenu.setRestaurant(restaurant);
        return specialMenuRepository.save(specialMenu);
    }

    @Override
    public SpecialMenu addSpecialMenu(Long restaurantId, SpecialMenu specialMenu) {
        // This is essentially the same as createSpecialMenu in this implementation
        return createSpecialMenu(restaurantId, specialMenu);
    }

    @Override
    public List<SpecialMenu> getActiveSpecialMenus(Long restaurantId) {
        // Verify the restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        // Get all active special menus for the restaurant
        LocalDateTime now = LocalDateTime.now();
        return specialMenuRepository.findByRestaurantIdAndEndDateAfterAndStartDateBefore(
            restaurantId, now, now);
    }

    @Override
    public List<SpecialMenu> getSpecialMenus(Long restaurantId) {
        // Verify the restaurant exists
        if (!restaurantRepository.existsById(restaurantId)) {
            throw new RuntimeException("Restaurant not found with id: " + restaurantId);
        }
        
        return specialMenuRepository.findByRestaurantId(restaurantId);
    }

    @Override
    public void updateMenuItemAvailability(Long restaurantId, Long itemId, boolean available) {
        // Verify the menu item exists and belongs to the restaurant
        MenuItem existingItem = menuItemRepository.findById(itemId)
            .orElseThrow(() -> new RuntimeException("Menu item not found with id: " + itemId));
            
        if (!existingItem.getRestaurant().getId().equals(restaurantId)) {
            throw new RuntimeException("Menu item does not belong to restaurant with id: " + restaurantId);
        }
        
        existingItem.setIsAvailable(available);
        menuItemRepository.save(existingItem);
    }
}