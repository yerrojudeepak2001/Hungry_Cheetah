package com.foodapp.notification.model;

public enum NotificationStatus {
    PENDING("pending"),
    SENT("sent"),
    DELIVERED("delivered"),
    READ("read"),
    FAILED("failed"),
    EXPIRED("expired"),
    CANCELLED("cancelled");
    
    private final String value;
    
    NotificationStatus(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static NotificationStatus fromValue(String value) {
        for (NotificationStatus status : NotificationStatus.values()) {
            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Unknown notification status: " + value);
    }
}