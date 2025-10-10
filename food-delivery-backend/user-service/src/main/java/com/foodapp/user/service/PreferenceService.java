package com.foodapp.user.service;

import com.foodapp.user.model.Preference;
import com.foodapp.user.model.User;
import com.foodapp.user.repository.PreferenceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
}