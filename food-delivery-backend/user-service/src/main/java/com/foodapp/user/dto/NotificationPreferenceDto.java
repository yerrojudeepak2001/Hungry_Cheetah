package com.foodapp.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import jakarta.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationPreferenceDto {
    private String userId;
    
    @NotNull(message = "Email notifications preference is required")
    private Boolean emailNotifications = true;
    
    @NotNull(message = "SMS notifications preference is required")
    private Boolean smsNotifications = true;
    
    @NotNull(message = "Push notifications preference is required")
    private Boolean pushNotifications = true;
    
    // Order-related notifications
    @NotNull(message = "Order confirmation preference is required")
    private Boolean orderConfirmation = true;
    
    @NotNull(message = "Order status updates preference is required")
    private Boolean orderStatusUpdates = true;
    
    @NotNull(message = "Delivery updates preference is required")
    private Boolean deliveryUpdates = true;
    
    // Marketing notifications
    @NotNull(message = "Promotional notifications preference is required")
    private Boolean promotionalNotifications = false;
    
    @NotNull(message = "Special offers preference is required")
    private Boolean specialOffers = false;
    
    @NotNull(message = "Restaurant recommendations preference is required")
    private Boolean restaurantRecommendations = true;
    
    // Security notifications
    @NotNull(message = "Security alerts preference is required")
    private Boolean securityAlerts = true;
    
    @NotNull(message = "Account activity preference is required")
    private Boolean accountActivity = true;
    
    // Timing preferences
    private String quietHoursStart; // Format: "22:00"
    private String quietHoursEnd; // Format: "08:00"
    private String timezone;
    
    // Frequency preferences
    private String emailFrequency; // IMMEDIATE, DAILY, WEEKLY
    private String smsFrequency; // IMMEDIATE, URGENT_ONLY
}