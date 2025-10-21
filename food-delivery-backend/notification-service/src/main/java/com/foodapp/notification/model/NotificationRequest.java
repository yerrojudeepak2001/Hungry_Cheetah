package com.foodapp.notification.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.Map;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationRequest {
    @NotNull
    private Long userId;
    
    @NotBlank
    private String title;
    
    @NotBlank
    private String message;
    
    @NotNull
    private NotificationChannel channel;
    
    @Builder.Default
    private NotificationPriority priority = NotificationPriority.MEDIUM;
    
    private String notificationType;
    private String templateId;
    private String actionUrl;
    private String imageUrl;
    private String category;
    private Long referenceId;
    private LocalDateTime scheduledAt;
    private LocalDateTime expiresAt;
    private Map<String, String> metadata;
    private Map<String, Object> templateData;
}