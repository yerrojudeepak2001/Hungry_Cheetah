package com.foodapp.user.service;

import com.foodapp.user.model.UserPreference;
import com.foodapp.user.model.User;
import com.foodapp.user.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PreferenceService {
    private final UserPreferenceRepository preferenceRepository;

    public PreferenceService(UserPreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @Transactional
    public UserPreference createPreference(User user, UserPreference preference) {
        preference.setUser(user);
        return preferenceRepository.save(preference);
    }

    @Transactional
    public UserPreference updatePreference(Long userId, UserPreference newPreference) {
        UserPreference existingPreference = preferenceRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));

        existingPreference.setDietaryPreferences(newPreference.getDietaryPreferences());
        existingPreference.setCuisinePreferences(newPreference.getCuisinePreferences());
        existingPreference.setAllergens(newPreference.getAllergens());
        existingPreference.setSpiceTolerance(newPreference.getSpiceTolerance());
        existingPreference.setPreferredPriceRange(newPreference.getPreferredPriceRange());
        existingPreference.setMealPreferences(newPreference.getMealPreferences());

        return preferenceRepository.save(existingPreference);
    }

    public UserPreference getPreference(Long userId) {
        return preferenceRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));
    }

    @Transactional
    public void deletePreference(Long userId) {
        UserPreference preference = preferenceRepository.findByUserId(userId)
            .orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));
        preferenceRepository.delete(preference);
    }
}