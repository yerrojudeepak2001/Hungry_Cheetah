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
public class UserEmailVerificationMessage implements Serializable {
    private String userId;
    private String email;
    private String verificationToken;
    private String verificationType; // REGISTRATION, EMAIL_CHANGE
    private long expiresAt;
    private long timestamp;
}