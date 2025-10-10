package com.foodapp.cart.service;

import com.foodapp.cart.model.Cart;
import com.foodapp.cart.model.CartItem;

public interface CartValidationService {
    boolean validateCart(Cart cart);
    boolean validateCartItem(CartItem cartItem);
    boolean validateItemAvailability(String itemId, Integer quantity);
    boolean validateRestaurantOperatingHours(String restaurantId);
    boolean validateDeliveryArea(String restaurantId, String deliveryAddress);
    boolean validateMinOrderAmount(Cart cart);
}