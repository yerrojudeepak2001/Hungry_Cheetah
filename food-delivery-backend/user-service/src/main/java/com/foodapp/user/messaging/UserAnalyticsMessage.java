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
public class UserAnalyticsMessage implements Serializable {
    private String eventType; // LOGIN, REGISTER, PROFILE_UPDATE, ORDER_PLACED, etc.
    private String userId;
    private Map<String, Object> eventData;
    private long timestamp;
}