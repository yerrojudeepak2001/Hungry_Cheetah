package com.foodapp.restaurant.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.restaurant.model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/restaurants")
public class RestaurantController {
    private final RestaurantService restaurantService;
    private final MenuService menuService;
    private final ReviewService reviewService;

    public RestaurantController(RestaurantService restaurantService,
                              MenuService menuService,
                              ReviewService reviewService) {
        this.restaurantService = restaurantService;
        this.menuService = menuService;
        this.reviewService = reviewService;
    }

    // Restaurant Management
    @PostMapping
    public ResponseEntity<ApiResponse<?>> registerRestaurant(@RequestBody Restaurant restaurant) {
        var registeredRestaurant = restaurantService.registerRestaurant(restaurant);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant registered successfully", registeredRestaurant));
    }

    @GetMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> getRestaurantDetails(@PathVariable Long restaurantId) {
        var restaurant = restaurantService.getRestaurantDetails(restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant details fetched successfully", restaurant));
    }

    @PutMapping("/{restaurantId}")
    public ResponseEntity<ApiResponse<?>> updateRestaurantDetails(
            @PathVariable Long restaurantId,
            @RequestBody Restaurant restaurant) {
        var updatedRestaurant = restaurantService.updateRestaurant(restaurantId, restaurant);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant details updated successfully", updatedRestaurant));
    }

    // Menu Management
    @PostMapping("/{restaurantId}/menu-items")
    public ResponseEntity<ApiResponse<?>> addMenuItem(
            @PathVariable Long restaurantId,
            @RequestBody MenuItem item) {
        var addedItem = menuService.addMenuItem(restaurantId, item);
        return ResponseEntity.ok(new ApiResponse<>(true, "Menu item added successfully", addedItem));
    }

    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<ApiResponse<?>> getRestaurantMenu(@PathVariable Long restaurantId) {
        var menu = menuService.getRestaurantMenu(restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Menu fetched successfully", menu));
    }

    @PutMapping("/{restaurantId}/menu-items/{itemId}")
    public ResponseEntity<ApiResponse<?>> updateMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId,
            @RequestBody MenuItem item) {
        var updatedItem = menuService.updateMenuItem(restaurantId, itemId, item);
        return ResponseEntity.ok(new ApiResponse<>(true, "Menu item updated successfully", updatedItem));
    }

    @DeleteMapping("/{restaurantId}/menu-items/{itemId}")
    public ResponseEntity<ApiResponse<?>> removeMenuItem(
            @PathVariable Long restaurantId,
            @PathVariable Long itemId) {
        menuService.removeMenuItem(restaurantId, itemId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Menu item removed successfully", null));
    }

    // Special Menus
    @PostMapping("/{restaurantId}/special-menus")
    public ResponseEntity<ApiResponse<?>> addSpecialMenu(
            @PathVariable Long restaurantId,
            @RequestBody SpecialMenu menu) {
        var addedMenu = menuService.addSpecialMenu(restaurantId, menu);
        return ResponseEntity.ok(new ApiResponse<>(true, "Special menu added successfully", addedMenu));
    }

    @GetMapping("/{restaurantId}/special-menus")
    public ResponseEntity<ApiResponse<?>> getSpecialMenus(@PathVariable Long restaurantId) {
        var menus = menuService.getSpecialMenus(restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Special menus fetched successfully", menus));
    }

    // Reviews and Ratings
    @PostMapping("/{restaurantId}/reviews")
    public ResponseEntity<ApiResponse<?>> addReview(
            @PathVariable Long restaurantId,
            @RequestBody Review review) {
        var addedReview = reviewService.addReview(restaurantId, review);
        return ResponseEntity.ok(new ApiResponse<>(true, "Review added successfully", addedReview));
    }

    @GetMapping("/{restaurantId}/reviews")
    public ResponseEntity<ApiResponse<?>> getRestaurantReviews(
            @PathVariable Long restaurantId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        var reviews = reviewService.getRestaurantReviews(restaurantId, page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "Reviews fetched successfully", reviews));
    }

    // Restaurant Analytics
    @GetMapping("/{restaurantId}/analytics/orders")
    public ResponseEntity<ApiResponse<?>> getOrderAnalytics(
            @PathVariable Long restaurantId,
            @RequestParam String timeFrame) {
        var analytics = restaurantService.getOrderAnalytics(restaurantId, timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Order analytics fetched successfully", analytics));
    }

    @GetMapping("/{restaurantId}/analytics/popular-items")
    public ResponseEntity<ApiResponse<?>> getPopularItems(
            @PathVariable Long restaurantId,
            @RequestParam String timeFrame) {
        var popularItems = restaurantService.getPopularItems(restaurantId, timeFrame);
        return ResponseEntity.ok(new ApiResponse<>(true, "Popular items fetched successfully", popularItems));
    }

    // AR Menu
    @PostMapping("/{restaurantId}/ar-menu")
    public ResponseEntity<ApiResponse<?>> uploadARMenu(
            @PathVariable Long restaurantId,
            @RequestBody ARMenu arMenu) {
        var uploaded = restaurantService.uploadARMenu(restaurantId, arMenu);
        return ResponseEntity.ok(new ApiResponse<>(true, "AR menu uploaded successfully", uploaded));
    }

    @GetMapping("/{restaurantId}/ar-menu")
    public ResponseEntity<ApiResponse<?>> getARMenu(@PathVariable Long restaurantId) {
        var arMenu = restaurantService.getARMenu(restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "AR menu fetched successfully", arMenu));
    }

    // Virtual Tour
    @PostMapping("/{restaurantId}/virtual-tour")
    public ResponseEntity<ApiResponse<?>> uploadVirtualTour(
            @PathVariable Long restaurantId,
            @RequestBody VirtualTour tour) {
        var uploaded = restaurantService.uploadVirtualTour(restaurantId, tour);
        return ResponseEntity.ok(new ApiResponse<>(true, "Virtual tour uploaded successfully", uploaded));
    }

    @GetMapping("/{restaurantId}/virtual-tour")
    public ResponseEntity<ApiResponse<?>> getVirtualTour(@PathVariable Long restaurantId) {
        var tour = restaurantService.getVirtualTour(restaurantId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Virtual tour fetched successfully", tour));
    }
}