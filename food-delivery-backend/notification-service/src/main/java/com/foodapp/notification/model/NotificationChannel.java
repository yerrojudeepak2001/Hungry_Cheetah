package com.foodapp.notification.model;

public enum NotificationChannel {
    EMAIL("email"),
    SMS("sms"),
    PUSH("push"),
    IN_APP("in_app"),
    WHATSAPP("whatsapp");
    
    private final String value;
    
    NotificationChannel(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static NotificationChannel fromValue(String value) {
        for (NotificationChannel channel : NotificationChannel.values()) {
            if (channel.value.equalsIgnoreCase(value)) {
                return channel;
            }
        }
        throw new IllegalArgumentException("Unknown notification channel: " + value);
    }
}