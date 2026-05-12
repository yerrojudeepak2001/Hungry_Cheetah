package com.foodapp.user.messaging;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserPasswordResetMessage implements Serializable {
    private String userId;
    private String email;
    private String resetToken;
    private String requestMethod; // EMAIL, SMS
    private String ipAddress;
    private String userAgent;
    private long expiresAt;
    private long timestamp;
}