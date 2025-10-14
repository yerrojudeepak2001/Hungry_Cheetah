package com.foodapp.user.controller;

import com.foodapp.common.dto.ApiResponse;
import com.foodapp.user.dto.DietaryRestrictionsRequest;
import com.foodapp.user.model.Preference;
import com.foodapp.user.model.UserPreference;
import com.foodapp.user.service.PreferenceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/users/{userId}/preferences")
public class PreferenceController {

    private final PreferenceService preferenceService;

    public PreferenceController(PreferenceService preferenceService) {
        this.preferenceService = preferenceService;
    }

    @GetMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserPreference>> getUserPreferences(@PathVariable Long userId) {
        UserPreference preferences = preferenceService.getPreference(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Preferences fetched successfully", preferences));
    }

    @PutMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserPreference>> updatePreferences(
            @PathVariable Long userId,
            @Valid @RequestBody UserPreference preferences) {
        UserPreference updatedPreferences = preferenceService.updatePreference(userId, preferences);
        return ResponseEntity.ok(new ApiResponse<>(true, "Preferences updated successfully", updatedPreferences));
    }

    @DeleteMapping
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Void>> deletePreferences(@PathVariable Long userId) {
        preferenceService.deletePreference(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Preferences deleted successfully", null));
    }

    @GetMapping("/dietary")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<Set<String>>> getDietaryPreferences(@PathVariable Long userId) {
        Set<String> dietaryPreferences = preferenceService.getDietaryPreferences(userId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Dietary preferences fetched successfully", dietaryPreferences));
    }

    @PutMapping("/dietary")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<UserPreference>> updateDietaryPreferences(
            @PathVariable Long userId,
            @Valid @RequestBody DietaryRestrictionsRequest request) {
        UserPreference updatedPreferences = preferenceService.updateDietaryPreferences(userId, request.getRestrictions());
        return ResponseEntity.ok(new ApiResponse<>(true, "Dietary preferences updated successfully", updatedPreferences));
    }

    @PostMapping("/category/{category}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Preference>>> createPreferencesByCategory(
            @PathVariable Long userId,
            @PathVariable String category,
            @RequestBody List<String> values) {
        List<Preference> preferences = preferenceService.createOrUpdatePreferences(userId, category, values);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(true, "Category preferences created successfully", preferences));
    }

    @GetMapping("/category/{category}")
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    public ResponseEntity<ApiResponse<List<Preference>>> getPreferencesByCategory(
            @PathVariable Long userId,
            @PathVariable String category) {
        List<Preference> preferences = preferenceService.getActivePreferencesByCategory(userId, category);
        return ResponseEntity.ok(new ApiResponse<>(true, "Category preferences fetched successfully", preferences));
    }
}