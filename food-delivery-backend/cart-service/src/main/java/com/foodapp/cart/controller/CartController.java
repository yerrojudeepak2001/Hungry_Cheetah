package com.foodapp.cart.controller;

import com.foodapp.cart.dto.ApiResponse;
import com.foodapp.cart.entity.Cart;
import com.foodapp.cart.entity.CartItem;
import com.foodapp.cart.service.CartService;
import com.foodapp.cart.service.CartValidationService;
import com.foodapp.cart.dto.CartItemUpdate;
import com.foodapp.cart.dto.MultiRestaurantCartItem;
import com.foodapp.cart.security.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    private final CartValidationService validationService;
    private final JwtUtil jwtUtil;

    public CartController(CartService cartService, CartValidationService validationService, JwtUtil jwtUtil) {
        this.cartService = cartService;
        this.validationService = validationService;
        this.jwtUtil = jwtUtil;
    }

    // Cart Management
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<?>> addToCart(
            @RequestBody CartItem item,
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            // Extract token from Authorization header if available
            String token = authToken;
            if (token == null && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }

            String userId = jwtUtil.getUserIdFromHeaders(token, userIdHeader);

            if (userId == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Unauthorized", null));
            }

            cartService.addToCart(Long.parseLong(userId), item);
            return ResponseEntity.ok(new ApiResponse<>(true, "Item added to cart successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to add item to cart: " + e.getMessage(), null));
        }
    }

    @GetMapping("/my-cart")
    public ResponseEntity<ApiResponse<?>> getMyCart(
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            // Extract token from Authorization header if available
            String token = authToken;
            if (token == null && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }

            String userId = jwtUtil.getUserIdFromHeaders(token, userIdHeader);
            if (userId == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Unauthorized", null));
            }

            var cart = cartService.getCart(Long.parseLong(userId));
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart fetched successfully", cart));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to fetch cart: " + e.getMessage(), null));
        }
    }

    @PutMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse<?>> updateCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestBody CartItemUpdate update,
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            // Extract token from Authorization header if available
            String token = authToken;
            if (token == null && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }

            String authenticatedUserId = jwtUtil.getUserIdFromHeaders(token, userIdHeader);
            if (authenticatedUserId == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Unauthorized", null));
            }

            // Use the authenticated user ID instead of path parameter for security
            var updated = cartService.updateCartItem(authenticatedUserId, itemId, update);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart item updated successfully", updated));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to update cart item: " + e.getMessage(), null));
        }
    }

    @DeleteMapping("/{userId}/items/{itemId}")
    public ResponseEntity<ApiResponse<?>> removeCartItem(
            @PathVariable Long userId,
            @PathVariable Long itemId,
            @RequestHeader(value = "X-User-Id", required = false) String userIdHeader,
            @RequestHeader(value = "X-Auth-Token", required = false) String authToken,
            @RequestHeader(value = "Authorization", required = false) String authorizationHeader) {
        try {
            // Extract token from Authorization header if available
            String token = authToken;
            if (token == null && authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                token = authorizationHeader.substring(7);
            }

            String authenticatedUserId = jwtUtil.getUserIdFromHeaders(token, userIdHeader);

            if (authenticatedUserId == null) {
                return ResponseEntity.status(401)
                        .body(new ApiResponse<>(false, "Unauthorized", null));
            }

            // Use the authenticated user ID instead of path parameter for security
            cartService.removeFromCart(Long.parseLong(authenticatedUserId), itemId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Item removed from cart successfully", null));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Failed to remove item from cart: " + e.getMessage(), null));
        }
    } // Cart Validation

    @PostMapping("/{userId}/validate")
    public ResponseEntity<ApiResponse<?>> validateCart(@PathVariable Long userId) {
        cartService.validateCartItems(String.valueOf(userId));
        return ResponseEntity.ok(new ApiResponse<>(true, "Cart validation completed", null));
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