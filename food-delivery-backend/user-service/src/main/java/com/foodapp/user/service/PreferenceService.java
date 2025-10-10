package com.foodapp.user.service;

<<<<<<< HEAD
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
=======
import com.foodapp.user.model.Preference;
import com.foodapp.user.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PreferenceService {
    
    public List<Preference> getUserPreferences(String userId) {
        // TODO: Implement preference retrieval
        return List.of();
    }
    
    public Preference savePreference(Preference preference) {
        // TODO: Implement preference saving
        return preference;
    }
    
    public void deletePreference(Long preferenceId) {
        // TODO: Implement preference deletion
    }
    
    public Preference setPreferences(Long userId, Preference preference) {
        // TODO: Implement set preferences
        return preference;
    }
    
    public List<Preference> getPreferences(Long userId) {
        // TODO: Implement get preferences
        return List.of();
    }
    
    public List<String> setDietaryRestrictions(Long userId, List<String> restrictions) {
        // TODO: Implement set dietary restrictions
        return restrictions;
    }
    
    public List<String> getDietaryRestrictions(Long userId) {
        // TODO: Implement get dietary restrictions
        return List.of();
>>>>>>> version1.4
    }
}