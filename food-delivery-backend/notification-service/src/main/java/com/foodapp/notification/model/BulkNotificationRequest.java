package com.foodapp.notification.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BulkNotificationRequest {
    @NotEmpty
    private List<Long> userIds;
    
    @NotNull
    private String title;
    
    @NotNull
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
    private Map<String, String> metadata;
    private Map<String, Object> templateData;
    
    // For personalized bulk notifications
    private Map<Long, Map<String, Object>> userSpecificData;
}