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
public class NotificationMessage implements Serializable {
    private String type; // EMAIL, SMS, PUSH
    private String recipient;
    private String subject;
    private String content;
    private Map<String, Object> metadata;
    private long timestamp;
}