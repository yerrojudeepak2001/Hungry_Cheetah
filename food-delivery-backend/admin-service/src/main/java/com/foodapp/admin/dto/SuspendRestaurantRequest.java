package com.foodapp.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;

public class SuspendRestaurantRequest {
    
    @NotBlank(message = "Suspension reason is required")
    @Size(max = 500, message = "Suspension reason cannot exceed 500 characters")
    @JsonProperty("reason")
    private String reason;
    
    @JsonProperty("adminId")
    private String adminId;
    
    @JsonProperty("category")
    private String category; // e.g., "QUALITY_VIOLATION", "COMPLIANCE", "SAFETY", "OTHER"
    
    @JsonProperty("severity")
    private String severity; // e.g., "LOW", "MEDIUM", "HIGH", "CRITICAL"
    
    @JsonProperty("suspensionDuration")
    private Integer suspensionDuration; // in days, null for indefinite
    
    @JsonProperty("suspensionEndDate")
    private LocalDateTime suspensionEndDate;
    
    @JsonProperty("notes")
    private String notes;
    
    @JsonProperty("notifyRestaurant")
    private Boolean notifyRestaurant = true;
    
    @JsonProperty("requiresReview")
    private Boolean requiresReview = false;
    
    // Default constructor
    public SuspendRestaurantRequest() {}
    
    // Constructor
    public SuspendRestaurantRequest(String reason, String adminId, String category, String severity,
                                   Integer suspensionDuration, LocalDateTime suspensionEndDate,
                                   String notes, Boolean notifyRestaurant, Boolean requiresReview) {
        this.reason = reason;
        this.adminId = adminId;
        this.category = category;
        this.severity = severity;
        this.suspensionDuration = suspensionDuration;
        this.suspensionEndDate = suspensionEndDate;
        this.notes = notes;
        this.notifyRestaurant = notifyRestaurant;
        this.requiresReview = requiresReview;
    }
    
    // Getters and Setters
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getAdminId() {
        return adminId;
    }
    
    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getSeverity() {
        return severity;
    }
    
    public void setSeverity(String severity) {
        this.severity = severity;
    }
    
    public Integer getSuspensionDuration() {
        return suspensionDuration;
    }
    
    public void setSuspensionDuration(Integer suspensionDuration) {
        this.suspensionDuration = suspensionDuration;
    }
    
    public LocalDateTime getSuspensionEndDate() {
        return suspensionEndDate;
    }
    
    public void setSuspensionEndDate(LocalDateTime suspensionEndDate) {
        this.suspensionEndDate = suspensionEndDate;
    }
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Boolean getNotifyRestaurant() {
        return notifyRestaurant;
    }
    
    public void setNotifyRestaurant(Boolean notifyRestaurant) {
        this.notifyRestaurant = notifyRestaurant;
    }
    
    public Boolean getRequiresReview() {
        return requiresReview;
    }
    
    public void setRequiresReview(Boolean requiresReview) {
        this.requiresReview = requiresReview;
    }
    
    @Override
    public String toString() {
        return "SuspendRestaurantRequest{" +
                "reason='" + reason + '\'' +
                ", adminId='" + adminId + '\'' +
                ", category='" + category + '\'' +
                ", severity='" + severity + '\'' +
                ", suspensionDuration=" + suspensionDuration +
                ", suspensionEndDate=" + suspensionEndDate +
                ", notes='" + notes + '\'' +
                ", notifyRestaurant=" + notifyRestaurant +
                ", requiresReview=" + requiresReview +
                '}';
    }
}