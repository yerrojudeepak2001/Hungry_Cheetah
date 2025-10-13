package com.foodapp.user.service;

import com.foodapp.user.model.User;
import com.foodapp.user.model.UserPreference;
import com.foodapp.user.repository.UserPreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

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

        // ✅ Update only non-null fields from newPreference
        if (newPreference.getDietaryPreferences() != null) {
            existingPreference.setDietaryPreferences(newPreference.getDietaryPreferences());
        }
        if (newPreference.getCuisinePreferences() != null) {
            existingPreference.setCuisinePreferences(newPreference.getCuisinePreferences());
        }
        if (newPreference.getAllergies() != null) {
            existingPreference.setAllergies(newPreference.getAllergies());
        }
        if (newPreference.getSpicyLevel() != null) {
            existingPreference.setSpicyLevel(newPreference.getSpicyLevel());
        }
        if (newPreference.getMaxPrice() != null) {
            existingPreference.setMaxPrice(newPreference.getMaxPrice());
        }
        if (newPreference.getMaxDeliveryTime() != null) {
            existingPreference.setMaxDeliveryTime(newPreference.getMaxDeliveryTime());
        }
        if (newPreference.getNotificationPreferences() != null) {
            existingPreference.setNotificationPreferences(newPreference.getNotificationPreferences());
        }
        if (newPreference.getPaymentPreference() != null) {
            existingPreference.setPaymentPreference(newPreference.getPaymentPreference());
        }
        if (newPreference.getOrderNotes() != null) {
            existingPreference.setOrderNotes(newPreference.getOrderNotes());
        }
        if (newPreference.getVegetarian() != null) {
            existingPreference.setVegetarian(newPreference.getVegetarian());
        }
        if (newPreference.getVegan() != null) {
            existingPreference.setVegan(newPreference.getVegan());
        }
        if (newPreference.getContactlessDelivery() != null) {
            existingPreference.setContactlessDelivery(newPreference.getContactlessDelivery());
        }
        if (newPreference.getSavePaymentInfo() != null) {
            existingPreference.setSavePaymentInfo(newPreference.getSavePaymentInfo());
        }
        if (newPreference.getAutoApplyCoupons() != null) {
            existingPreference.setAutoApplyCoupons(newPreference.getAutoApplyCoupons());
        }

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

    // ✅ Added missing methods used by UserController

    /**
     * Update dietary preferences (like restrictions: vegan, gluten-free, etc.)
     */
    @Transactional
    public UserPreference updateDietaryPreferences(Long userId, List<String> restrictions) {
        UserPreference preference = preferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));

        Set<String> newRestrictions = new HashSet<>(restrictions);
        preference.setDietaryPreferences(newRestrictions);

        return preferenceRepository.save(preference);
    }

    /**
     * Fetch dietary preferences for a given user.
     */
    public Set<String> getDietaryPreferences(Long userId) {
        UserPreference preference = preferenceRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Preferences not found for user: " + userId));
        return preference.getDietaryPreferences();
    }
}
