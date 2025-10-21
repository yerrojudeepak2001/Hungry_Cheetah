package com.foodapp.user.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAccountStatusMessage implements Serializable {
    private String userId;
    private String email;
    private String oldStatus;
    private String newStatus; // ACTIVE, SUSPENDED, DEACTIVATED, BANNED
    private String reason;
    private String changedBy; // ADMIN, SYSTEM, USER
    private String adminUserId; // if changed by admin
    private Map<String, Object> metadata;
    private long timestamp;
}