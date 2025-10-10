package com.foodapp.cart.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.cart.entity.Cart;
import com.foodapp.cart.entity.CartItem;
import com.foodapp.cart.service.CartService;
import com.foodapp.cart.service.CartValidationService;
import com.foodapp.cart.dto.CartItemUpdate;
import com.foodapp.cart.dto.MultiRestaurantCartItem;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CartValidationService validationService;

    public CartController(CartService cartService, CartValidationService validationService) {
        this.cartService = cartService;
        this.validationService = validationService;
    }

    // Cart Management
    @PostMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> createCart(
            @PathVariable Long userId,
            @RequestBody CartItem item) {
        var cart = cartService.addToCart(userId, item);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item added to cart successfully", cart));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getCart(@PathVariable Long userId) {
        var cart = cartService.getCart(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart fetched successfully", cart));
    }

    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse<?>> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody CartItemUpdate update) {
        var updated = cartService.updateCartItem(String.valueOf(userId), itemId, update);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart item updated successfully", updated));
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse<?>> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        cartService.removeFromCart(userId, itemId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item removed from cart successfully", null));
    }

    // Cart Validation
    @PostMapping("/{userId}/validate")
    public ResponseEntity<ApiResponse<?>> validateCart(@PathVariable Long userId) {
        var validation = validationService.validateCart(String.valueOf(userId));
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart validation completed", validation));
    }

    // Smart Features
    @GetMapping("/{userId}/suggestions")
    public ResponseEntity<ApiResponse<?>> getCartSuggestions(@PathVariable Long userId) {
        var suggestions = cartService.getSmartSuggestions(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart suggestions fetched successfully", suggestions));
    }

    @PostMapping("/{userId}/combo-deals")
    public ResponseEntity<ApiResponse<?>> applyComboDeals(@PathVariable Long userId) {
        var deals = cartService.applyComboDeals(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Combo deals applied successfully", deals));
    }

    // Multi-restaurant Cart
    @PostMapping("/{userId}/multi-restaurant")
    public ResponseEntity<ApiResponse<?>> addToMultiRestaurantCart(
            @PathVariable Long userId,
            @RequestBody MultiRestaurantCartItem item) {
        var cart = cartService.addToMultiRestaurantCart(userId, item);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item added to multi-restaurant cart successfully", cart));
    }

    @GetMapping("/{userId}/multi-restaurant")
    public ResponseEntity<ApiResponse<?>> getMultiRestaurantCart(@PathVariable Long userId) {
        var cart = cartService.getMultiRestaurantCart(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Multi-restaurant cart fetched successfully", cart));
    }

    // Save for Later
    @PostMapping("/{userId}/save-for-later/{itemId}")
    public ResponseEntity<ApiResponse<?>> saveForLater(
            @PathVariable Long userId,
            @PathVariable Long itemId) {
        cartService.saveForLater(userId, itemId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Item saved for later successfully", null));
    }

    @GetMapping("/{userId}/saved-items")
    public ResponseEntity<ApiResponse<?>> getSavedItems(@PathVariable Long userId) {
        var items = cartService.getSavedItems(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Saved items fetched successfully", items));
    }

    // Cart Summary
    @GetMapping("/{userId}/summary")
    public ResponseEntity<ApiResponse<?>> getCartSummary(@PathVariable Long userId) {
        var summary = cartService.getCartSummary(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart summary fetched successfully", summary));
    }
}