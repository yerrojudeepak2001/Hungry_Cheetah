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
public class UserProfileUpdatedMessage implements Serializable {
    private String userId;
    private String email;
    private String updateType; // PERSONAL_INFO, ADDRESS, PREFERENCES, etc.
    private Map<String, Object> oldValues;
    private Map<String, Object> newValues;
    private String updatedBy; // USER, ADMIN, SYSTEM
    private long timestamp;
}