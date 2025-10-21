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
public class UserRegisteredMessage implements Serializable {
    private String userId;
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
    private String registrationMethod; // EMAIL, GOOGLE, FACEBOOK, etc.
    private Map<String, Object> metadata;
    private long timestamp;
}