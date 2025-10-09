package com.foodapp.common.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Data
@Document(collection = "user_preferences")
public class UserPreferences {
    @Id
    private String id;
    private Long userId;
    
    // Dietary Preferences
    private List<String> dietaryPreferences; // VEGAN, VEGETARIAN, GLUTEN_FREE, etc.
    private Integer spiceLevel; // 1-5
    private List<String> allergies;
    private List<String> favoriteCuisines;
    
    // Delivery Preferences
    private String defaultAddress;
    private List<String> savedAddresses;
    private Boolean contactlessDelivery;
    private String deliveryInstructions;
    
    // Payment Preferences
    private String defaultPaymentMethod;
    private List<String> savedPaymentMethods;
    
    // Communication Preferences
    private Boolean pushNotificationsEnabled;
    private Boolean emailNotificationsEnabled;
    private Boolean smsNotificationsEnabled;
    private List<String> notificationTypes; // ORDER_STATUS, OFFERS, RECOMMENDATIONS
    
    // App Preferences
    private String preferredLanguage;
    private String currencyPreference;
    private Boolean darkModeEnabled;
    
    // Order History Based
    private List<Long> favoriteRestaurants;
    private List<Long> favoriteItems;
    private List<String> recentSearches;
}