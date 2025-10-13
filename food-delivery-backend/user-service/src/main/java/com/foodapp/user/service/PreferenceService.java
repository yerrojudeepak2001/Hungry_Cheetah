package com.foodapp.user.service;

import com.foodapp.user.model.Preference;
import com.foodapp.user.model.User;
import com.foodapp.user.model.UserPreference;
import com.foodapp.user.repository.PreferenceRepository;
import com.foodapp.user.repository.UserPreferenceRepository;
import com.foodapp.user.repository.UserRepository;
import com.foodapp.common.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PreferenceService {

    private final UserPreferenceRepository userPreferenceRepository;
    private final PreferenceRepository preferenceRepository;
    private final UserRepository userRepository;

    public PreferenceService(
            UserPreferenceRepository userPreferenceRepository, 
            PreferenceRepository preferenceRepository,
            UserRepository userRepository) {
        this.userPreferenceRepository = userPreferenceRepository;
        this.preferenceRepository = preferenceRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public Preference createUserPreference(User user, Preference preference) {
        preference.setUser(user);
        preference.setActive(true);
        return preferenceRepository.save(preference);
    }

    @Transactional
    public UserPreference updatePreference(Long userId, UserPreference newPreference) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        
        UserPreference existingPreference = userPreferenceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPreference userPref = new UserPreference();
                    userPref.setUser(user);
                    return userPref;
                });
        
        // Update fields from newPreference
        if (newPreference.getDietaryPreferences() != null) {
            existingPreference.setDietaryPreferences(newPreference.getDietaryPreferences());
        }
        if (newPreference.getAllergies() != null) {
            existingPreference.setAllergies(newPreference.getAllergies());
        }
        if (newPreference.getCuisinePreferences() != null) {
            existingPreference.setCuisinePreferences(newPreference.getCuisinePreferences());
        }
        if (newPreference.getVegetarian() != null) {
            existingPreference.setVegetarian(newPreference.getVegetarian());
        }
        if (newPreference.getVegan() != null) {
            existingPreference.setVegan(newPreference.getVegan());
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
        if (newPreference.getContactlessDelivery() != null) {
            existingPreference.setContactlessDelivery(newPreference.getContactlessDelivery());
        }
        if (newPreference.getNotificationPreferences() != null) {
            existingPreference.setNotificationPreferences(newPreference.getNotificationPreferences());
        }
        if (newPreference.getPaymentPreference() != null) {
            existingPreference.setPaymentPreference(newPreference.getPaymentPreference());
        }
        if (newPreference.getSavePaymentInfo() != null) {
            existingPreference.setSavePaymentInfo(newPreference.getSavePaymentInfo());
        }
        if (newPreference.getOrderNotes() != null) {
            existingPreference.setOrderNotes(newPreference.getOrderNotes());
        }
        if (newPreference.getAutoApplyCoupons() != null) {
            existingPreference.setAutoApplyCoupons(newPreference.getAutoApplyCoupons());
        }
        
        return userPreferenceRepository.save(existingPreference);
    }

    public UserPreference getPreference(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                
        return userPreferenceRepository.findByUserId(userId)
                .orElseGet(() -> {
                    UserPreference newPreference = new UserPreference();
                    newPreference.setUser(user);
                    return userPreferenceRepository.save(newPreference);
                });
    }

    @Transactional
    public void deletePreference(Long userId) {
        userPreferenceRepository.findByUserId(userId)
                .ifPresent(userPreferenceRepository::delete);
        
        // Also delete individual preferences
        preferenceRepository.deleteByUserId(userId);
    }
    
    @Transactional
    public UserPreference updateDietaryPreferences(Long userId, List<String> restrictions) {
        UserPreference userPreference = getPreference(userId);
        userPreference.setDietaryPreferences(new HashSet<>(restrictions));
        return userPreferenceRepository.save(userPreference);
    }
    
    public Set<String> getDietaryPreferences(Long userId) {
        return userPreferenceRepository.findByUserId(userId)
                .map(UserPreference::getDietaryPreferences)
                .orElse(Collections.emptySet());
    }
    
    @Transactional
    public List<Preference> createOrUpdatePreferences(Long userId, String category, List<String> values) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
                
        // First deactivate all existing preferences in this category
        preferenceRepository.findByUserIdAndCategory(userId, category)
                .forEach(pref -> {
                    pref.setActive(false);
                    preferenceRepository.save(pref);
                });
                
        // Then create new preferences
        return values.stream().map(value -> {
            Preference pref = new Preference();
            pref.setUser(user);
            pref.setCategory(category);
            pref.setValue(value);
            pref.setType("USER_SELECTED");
            pref.setActive(true);
            return preferenceRepository.save(pref);
        }).collect(Collectors.toList());
    }
    
    public List<Preference> getActivePreferencesByCategory(Long userId, String category) {
        return preferenceRepository.findActivePreferencesByUserIdAndCategory(userId, category);
    }
}
