package com.foodapp.notification.model;

public enum NotificationPriority {
    LOW("low", 1),
    MEDIUM("medium", 2),
    HIGH("high", 3),
    URGENT("urgent", 4);
    
    private final String value;
    private final int level;
    
    NotificationPriority(String value, int level) {
        this.value = value;
        this.level = level;
    }
    
    public String getValue() {
        return value;
    }
    
    public int getLevel() {
        return level;
    }
    
    public static NotificationPriority fromValue(String value) {
        for (NotificationPriority priority : NotificationPriority.values()) {
            if (priority.value.equalsIgnoreCase(value)) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Unknown notification priority: " + value);
    }
}