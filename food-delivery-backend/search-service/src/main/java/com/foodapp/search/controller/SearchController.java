package com.foodapp.search.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.search.model.*;
import com.foodapp.search.dto.*;
import com.foodapp.search.service.SearchService;
import com.foodapp.search.service.RecommendationService;
import com.foodapp.search.service.FilterService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class SearchController {
    private final SearchService searchService;
    private final RecommendationService recommendationService;
    private final FilterService filterService;

    public SearchController(SearchService searchService,
                          RecommendationService recommendationService,
                          FilterService filterService) {
        this.searchService = searchService;
        this.recommendationService = recommendationService;
        this.filterService = filterService;
    }

    // Search Operations
    @GetMapping("/restaurants")
    public ResponseEntity<ApiResponse<?>> searchRestaurants(
            @RequestParam String query,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) List<String> cuisines,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var results = searchService.searchRestaurants(query, location, cuisines, page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "Restaurant search results fetched successfully", results));
    }

    @GetMapping("/dishes")
    public ResponseEntity<ApiResponse<?>> searchDishes(
            @RequestParam String query,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) List<String> categories,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        var results = searchService.searchDishes(query, location, categories, page, size);
        return ResponseEntity.ok(new ApiResponse<>(true, "Dish search results fetched successfully", results));
    }

    // Advanced Search Features
    @PostMapping("/advanced")
    public ResponseEntity<ApiResponse<?>> advancedSearch(@RequestBody AdvancedSearchRequest request) {
        var results = searchService.performAdvancedSearch(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Advanced search results fetched successfully", results));
    }

    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<?>> searchNearby(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "5") double radiusKm) {
        var results = searchService.searchNearby(latitude, longitude, radiusKm);
        return ResponseEntity.ok(new ApiResponse<>(true, "Nearby places fetched successfully", results));
    }

    // Filters
    @GetMapping("/filters")
    public ResponseEntity<ApiResponse<?>> getAvailableFilters() {
        var filters = filterService.getAvailableFilters();
        return ResponseEntity.ok(new ApiResponse<>(true, "Available filters fetched successfully", filters));
    }

    @PostMapping("/filter")
    public ResponseEntity<ApiResponse<?>> applyFilters(@RequestBody FilterRequest filters) {
        var results = filterService.applyFilters(filters);
        return ResponseEntity.ok(new ApiResponse<>(true, "Filtered results fetched successfully", results));
    }

    // Recommendations
    @GetMapping("/recommendations/{userId}")
    public ResponseEntity<ApiResponse<?>> getPersonalizedRecommendations(
            @PathVariable Long userId,
            @RequestParam(required = false) String type) {
        var recommendations = recommendationService.getPersonalizedRecommendations(userId, type);
        return ResponseEntity.ok(new ApiResponse<>(true, "Recommendations fetched successfully", recommendations));
    }

    @GetMapping("/trending")
    public ResponseEntity<ApiResponse<?>> getTrendingItems(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category) {
        var trending = searchService.getTrendingItems(location, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Trending items fetched successfully", trending));
    }

    // Smart Search
    @PostMapping("/voice")
    public ResponseEntity<ApiResponse<?>> voiceSearch(@RequestBody VoiceSearchRequest request) {
        var results = searchService.processVoiceSearch(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Voice search results fetched successfully", results));
    }

    @PostMapping("/image")
    public ResponseEntity<ApiResponse<?>> imageSearch(@RequestBody ImageSearchRequest request) {
        var results = searchService.processImageSearch(request);
        return ResponseEntity.ok(new ApiResponse<>(true, "Image search results fetched successfully", results));
    }

    // Search History and Preferences
    @GetMapping("/history/{userId}")
    public ResponseEntity<ApiResponse<?>> getSearchHistory(@PathVariable Long userId) {
        var history = searchService.getSearchHistory(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Search history fetched successfully", history));
    }

    @PostMapping("/preferences/{userId}")
    public ResponseEntity<ApiResponse<?>> updateSearchPreferences(
            @PathVariable Long userId,
            @RequestBody SearchPreferences preferences) {
        var updated = searchService.updateSearchPreferences(userId, preferences);
        return ResponseEntity.ok(new ApiResponse<>(true, "Search preferences updated successfully", updated));
    }
}