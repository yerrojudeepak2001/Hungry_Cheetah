package com.foodapp.notification.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private Long userId;
    private String title;
    private String message;
    private String notificationType;
    private String channel; // EMAIL, SMS, PUSH, IN_APP
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime sentAt;
    private LocalDateTime readAt;
    private String priority; // HIGH, MEDIUM, LOW
    
    @ElementCollection
    private Map<String, String> metadata;
    
    private String templateId;
    private Boolean isRead;
    private String actionUrl;
    private String imageUrl;
    private String category;
    private Long referenceId; // orderId, promotionId, etc.
}