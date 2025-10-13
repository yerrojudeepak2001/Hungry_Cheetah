package com.foodapp.user.service;

import com.foodapp.user.model.User;
import com.foodapp.user.model.UserPreference;
import com.foodapp.user.repository.UserPreferenceRepository;
import com.foodapp.user.model.Preference;
import com.foodapp.user.model.User;
import com.foodapp.user.repository.PreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.HashSet;
import java.util.Set;

@Service
public class PreferenceService {

    private final UserPreferenceRepository preferenceRepository;

@Service
public class PreferenceService {
    private final PreferenceRepository preferenceRepository;

    public PreferenceService(PreferenceRepository preferenceRepository) {
        this.preferenceRepository = preferenceRepository;
    }

    @Transactional
    public Preference createUserPreference(User user, Preference preference) {
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
    public Preference updateUserPreference(Long userId, Preference newPreference) {
        List<Preference> existingPreferences = preferenceRepository.findByUserId(userId);
        
        if (!existingPreferences.isEmpty()) {
            Preference existingPreference = existingPreferences.get(0);
            existingPreference.setType(newPreference.getType());
            existingPreference.setValue(newPreference.getValue());
            existingPreference.setCategory(newPreference.getCategory());
            existingPreference.setActive(newPreference.getActive());
            return preferenceRepository.save(existingPreference);
        }
        
        return preferenceRepository.save(newPreference);
    }

    public List<Preference> getUserPreferences(Long userId) {
        return preferenceRepository.findByUserId(userId);
    }

    @Transactional
    public void deleteUserPreference(Long preferenceId) {
        preferenceRepository.deleteById(preferenceId);
    }
    
    public List<String> getDietaryRestrictions(Long userId) {
        List<Preference> preferences = preferenceRepository.findByUserIdAndCategory(userId, "DIETARY");
        return preferences.stream()
                .filter(Preference::getActive)
                .map(Preference::getValue)
                .toList();
    }
    
    public Preference setPreferences(Long userId, Preference preference) {
        preference.setUser(new User()); // This would need proper user lookup
        return createUserPreference(new User(), preference); // This would need proper user lookup
    }
    
    public List<Preference> getPreferences(Long userId) {
        return getUserPreferences(userId);
    }
    
    @Transactional
    public List<String> setDietaryRestrictions(Long userId, List<String> restrictions) {
        // Remove existing dietary restrictions
        List<Preference> existingDietary = preferenceRepository.findByUserIdAndCategory(userId, "DIETARY");
        preferenceRepository.deleteAll(existingDietary);
        
        // Add new dietary restrictions
        User user = new User(); // This would need proper user lookup
        for (String restriction : restrictions) {
            Preference pref = new Preference();
            pref.setUser(user);
            pref.setCategory("DIETARY");
            pref.setType("RESTRICTION");
            pref.setValue(restriction);
            pref.setActive(true);
            preferenceRepository.save(pref);
        }
        
        return restrictions;
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
