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
public class UserLoginMessage implements Serializable {
    private String userId;
    private String email;
    private String loginMethod; // PASSWORD, GOOGLE, FACEBOOK, etc.
    private String ipAddress;
    private String userAgent;
    private String deviceType; // WEB, MOBILE, TABLET
    private String location; // City, Country if available
    private boolean successful;
    private String failureReason; // if login failed
    private Map<String, Object> metadata;
    private long timestamp;
}