package com.foodapp.notification.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "notification_preferences")
public class NotificationPreferences {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotNull
    @Column(name = "user_id", unique = true)
    private Long userId;
    
    @Column(name = "email_enabled")
    @Builder.Default
    private Boolean emailEnabled = true;
    
    @Column(name = "sms_enabled")
    @Builder.Default
    private Boolean smsEnabled = true;
    
    @Column(name = "push_enabled")
    @Builder.Default
    private Boolean pushEnabled = true;
    
    @Column(name = "in_app_enabled")
    @Builder.Default
    private Boolean inAppEnabled = true;
    
    // Specific notification types
    @Column(name = "order_notifications")
    @Builder.Default
    private Boolean orderNotifications = true;
    
    @Column(name = "promotion_notifications")
    @Builder.Default
    private Boolean promotionNotifications = true;
    
    @Column(name = "delivery_notifications")
    @Builder.Default
    private Boolean deliveryNotifications = true;
    
    @Column(name = "marketing_notifications")
    @Builder.Default
    private Boolean marketingNotifications = false;
    
    @Column(name = "system_notifications")
    @Builder.Default
    private Boolean systemNotifications = true;
    
    // Quiet hours
    @Column(name = "quiet_hours_enabled")
    @Builder.Default
    private Boolean quietHoursEnabled = false;
    
    @Column(name = "quiet_hours_start")
    private Integer quietHoursStart; // Hour of day (0-23)
    
    @Column(name = "quiet_hours_end")
    private Integer quietHoursEnd; // Hour of day (0-23)
    
    @ElementCollection
    @Enumerated(EnumType.STRING)
    @CollectionTable(name = "user_preferred_channels", joinColumns = @JoinColumn(name = "preferences_id"))
    @Column(name = "channel")
    private Set<NotificationChannel> preferredChannels;
    
    @Column(name = "frequency_limit")
    @Builder.Default
    private Integer frequencyLimit = 10; // Max notifications per day
    
    @Column(name = "created_at")
    @Builder.Default
    private LocalDateTime createdAt = LocalDateTime.now();
    
    @Column(name = "updated_at")
    @Builder.Default
    private LocalDateTime updatedAt = LocalDateTime.now();
}