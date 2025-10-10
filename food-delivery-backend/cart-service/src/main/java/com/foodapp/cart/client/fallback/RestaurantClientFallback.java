package com.foodapp.cart.client.fallback;

import com.foodapp.cart.client.RestaurantClient;
import com.foodapp.cart.dto.MenuItemValidation;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Collections;

@Component
public class RestaurantClientFallback implements RestaurantClient {

    @Override
    public MenuItemValidation validateMenuItem(String itemId) {
        MenuItemValidation validation = new MenuItemValidation();
        validation.setItemId(itemId);
        validation.setIsValid(false);
        validation.setIsActive(false);
        validation.setPrice(BigDecimal.ZERO);
        validation.setErrorMessage("Restaurant service unavailable");
        return validation;
    }

    @Override
    public MenuItemValidation validateMenuItem(String restaurantId, String itemId) {
        MenuItemValidation validation = new MenuItemValidation();
        validation.setItemId(itemId);
        validation.setRestaurantId(restaurantId);
        validation.setIsValid(false);
        validation.setIsActive(false);
        validation.setPrice(BigDecimal.ZERO);
        validation.setErrorMessage("Restaurant service unavailable");
        return validation;
    }

    @Override
    public boolean checkRestaurantAvailability(String restaurantId) {
        return false;
    }

    @Override
    public List<MenuItemValidation> validateMenuItems(List<String> itemIds) {
        return Collections.emptyList();
    }

    @Override
    public List<MenuItemValidation> validateMenuItems(String restaurantId, List<String> itemIds) {
        return Collections.emptyList();
    }
}