package com.foodapp.cart.service.impl;

import com.foodapp.cart.service.CartValidationService;
import com.foodapp.cart.entity.Cart;
import com.foodapp.cart.entity.CartItem;
import com.foodapp.cart.client.RestaurantClient;
import com.foodapp.cart.client.InventoryClient;
import com.foodapp.cart.dto.StockStatus;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CartValidationServiceImpl implements CartValidationService {
    
    private final RestaurantClient restaurantClient;
    private final InventoryClient inventoryClient;

    @Override
    public boolean validateCart(Cart cart) {
        if (cart == null) {
            return false;
        }

        // Validate basic cart properties
        if (cart.getUserId() == null || cart.getUserId().trim().isEmpty()) {
            return false;
        }

        // Validate all cart items
        if (cart.getItems() != null) {
            for (CartItem item : cart.getItems()) {
                if (!validateCartItem(item)) {
                    return false;
                }
            }
        }

        // Validate minimum order amount
        if (!validateMinOrderAmount(cart)) {
            return false;
        }

        // Validate restaurant operating hours
        if (cart.getRestaurantId() != null && !validateRestaurantOperatingHours(cart.getRestaurantId())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean validateCartItem(CartItem cartItem) {
        if (cartItem == null) {
            return false;
        }

        // Validate basic item properties
        if (cartItem.getMenuItemId() == null || cartItem.getMenuItemId().trim().isEmpty()) {
            return false;
        }

        if (cartItem.getQuantity() == null || cartItem.getQuantity() <= 0) {
            return false;
        }

        if (cartItem.getPrice() == null || cartItem.getPrice().compareTo(BigDecimal.ZERO) < 0) {
            return false;
        }

        // Validate item availability
        if (!validateItemAvailability(cartItem.getMenuItemId(), cartItem.getQuantity())) {
            return false;
        }

        return true;
    }

    @Override
    public boolean validateItemAvailability(String itemId, Integer quantity) {
        if (itemId == null || quantity == null || quantity <= 0) {
            return false;
        }

        try {
            StockStatus stockStatus = inventoryClient.getItemStock(itemId);
            if (stockStatus == null) {
                return false; // Item not found
            }

            // Check if item is available
            if (!stockStatus.getIsAvailable()) {
                return false;
            }

            // Check if sufficient stock is available
            if (stockStatus.getAvailableStock() != null && stockStatus.getAvailableStock() < quantity) {
                return false;
            }

            return true;
        } catch (Exception e) {
            // If service call fails, assume item is not available
            return false;
        }
    }

    @Override
    public boolean validateRestaurantOperatingHours(String restaurantId) {
        if (restaurantId == null || restaurantId.trim().isEmpty()) {
            return false;
        }

        try {
            // Check restaurant availability using the available method
            return restaurantClient.checkRestaurantAvailability(restaurantId);
        } catch (Exception e) {
            // If service call fails, assume restaurant is closed
            return false;
        }
    }

    @Override
    public boolean validateDeliveryArea(String restaurantId, String deliveryAddress) {
        if (restaurantId == null || deliveryAddress == null || 
            restaurantId.trim().isEmpty() || deliveryAddress.trim().isEmpty()) {
            return false;
        }

        try {
            // Check if restaurant is available/operational
            return restaurantClient.checkRestaurantAvailability(restaurantId);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateMinOrderAmount(Cart cart) {
        if (cart == null) {
            return false;
        }

        // Set minimum order amount to $10
        BigDecimal minimumOrderAmount = new BigDecimal("10.00");
        BigDecimal cartTotal = cart.getTotalAmount() != null ? cart.getTotalAmount() : BigDecimal.ZERO;

        return cartTotal.compareTo(minimumOrderAmount) >= 0;
    }
}