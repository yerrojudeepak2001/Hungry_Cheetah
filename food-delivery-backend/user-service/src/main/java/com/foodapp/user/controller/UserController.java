package com.foodapp.user.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.user.model.User;
import com.foodapp.user.model.UserPreference;
import com.foodapp.user.model.Address;
import com.foodapp.user.service.UserService;
import com.foodapp.user.service.PreferenceService;
import com.foodapp.user.service.AddressService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PreferenceService preferenceService;
    private final AddressService addressService;

    public UserController(UserService userService,
                          PreferenceService preferenceService,
                          AddressService addressService) {
        this.userService = userService;
        this.preferenceService = preferenceService;
        this.addressService = addressService;
    }

    // 🧍 User Management
    @PostMapping("/register")
    public ResponseEntity<ApiResponse<?>> registerUser(@RequestBody User user) {
        var registeredUser = userService.registerUser(user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User registered successfully", registeredUser));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> getUserProfile(@PathVariable Long userId) {
        var user = userService.getUser(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "User profile fetched successfully", user));
    }

    @PutMapping("/{userId}")
    public ResponseEntity<ApiResponse<?>> updateUserProfile(
            @PathVariable Long userId,
            @RequestBody User user) {
        var updatedUser = userService.updateUser(userId, user);
        return ResponseEntity.ok(new ApiResponse<>(true, "User profile updated successfully", updatedUser));
    }

    // 🍽️ User Preferences
    @PostMapping("/{userId}/preferences")
    public ResponseEntity<ApiResponse<?>> setUserPreferences(
            @PathVariable Long userId,
            @RequestBody UserPreference preferences) {
        var updatedPreferences = preferenceService.updatePreference(userId, preferences);
        return ResponseEntity.ok(new ApiResponse<>(true, "Preferences updated successfully", updatedPreferences));
    }

    @GetMapping("/{userId}/preferences")
    public ResponseEntity<ApiResponse<?>> getUserPreferences(@PathVariable Long userId) {
        var preferences = preferenceService.getPreference(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Preferences fetched successfully", preferences));
    }

    // 🚫 Dietary Restrictions
    @PostMapping("/{userId}/dietary-restrictions")
    public ResponseEntity<ApiResponse<?>> setDietaryRestrictions(
            @PathVariable Long userId,
            @RequestBody List<String> restrictions) {
        var updated = preferenceService.updateDietaryPreferences(userId, restrictions);
        return ResponseEntity.ok(new ApiResponse<>(true, "Dietary restrictions updated successfully", updated));
    }

    @GetMapping("/{userId}/dietary-restrictions")
    public ResponseEntity<ApiResponse<?>> getDietaryRestrictions(@PathVariable Long userId) {
        var restrictions = preferenceService.getDietaryPreferences(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Dietary restrictions fetched successfully", restrictions));
    }

    // 🏠 Addresses
    @PostMapping("/{userId}/addresses")
    public ResponseEntity<ApiResponse<?>> addAddress(
            @PathVariable Long userId,
            @RequestBody Address address) {
        var addedAddress = addressService.addAddress(userId, address);
        return ResponseEntity.ok(new ApiResponse<>(true, "Address added successfully", addedAddress));
    }

    @GetMapping("/{userId}/addresses")
    public ResponseEntity<ApiResponse<?>> getUserAddresses(@PathVariable Long userId) {
        var addresses = addressService.getUserAddresses(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Addresses fetched successfully", addresses));
    }

    @PutMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<ApiResponse<?>> updateAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId,
            @RequestBody Address address) {
        var updatedAddress = addressService.updateAddress(userId, addressId, address);
        return ResponseEntity.ok(new ApiResponse<>(true, "Address updated successfully", updatedAddress));
    }

    @DeleteMapping("/{userId}/addresses/{addressId}")
    public ResponseEntity<ApiResponse<?>> deleteAddress(
            @PathVariable Long userId,
            @PathVariable Long addressId) {
        addressService.deleteAddress(userId, addressId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Address deleted successfully", null));
    }

    // 📊 User Stats and History
    @GetMapping("/{userId}/order-history")
    public ResponseEntity<ApiResponse<?>> getUserOrderHistory(@PathVariable Long userId) {
        var history = userService.getUserOrders(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order history fetched successfully", history));
    }

    @GetMapping("/{userId}/favorite-restaurants")
    public ResponseEntity<ApiResponse<?>> getFavoriteRestaurants(@PathVariable Long userId) {
        var favorites = userService.getFavoriteRestaurants(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Favorite restaurants fetched successfully", favorites));
    }

    @PostMapping("/{userId}/favorite-restaurants/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> addFavoriteRestaurant(
            @PathVariable Long userId,
            @PathVariable Long restaurantId) {
        userService.addFavoriteRestaurant(userId, restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant added to favorites", null));
    }

    @DeleteMapping("/{userId}/favorite-restaurants/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> removeFavoriteRestaurant(
            @PathVariable Long userId,
            @PathVariable Long restaurantId) {
        userService.removeFavoriteRestaurant(userId, restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant removed from favorites", null));
    }
}
