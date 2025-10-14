package com.foodapp.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RejectRestaurantRequest {
    
    @NotBlank(message = "Rejection reason is required")
    @Size(max = 500, message = "Rejection reason cannot exceed 500 characters")
    @JsonProperty("reason")
    private String reason;
    
    @JsonProperty("adminId")
    private String adminId;
    
    @JsonProperty("category")
    private String category; // e.g., "DOCUMENTATION", "COMPLIANCE", "OTHER"
    
    @JsonProperty("notes")
    private String notes;
    
    @JsonProperty("canReapply")
    private Boolean canReapply = true;
    
    // Default constructor
    public RejectRestaurantRequest() {}
    
    // Constructor
    public RejectRestaurantRequest(String reason, String adminId, String category, String notes, Boolean canReapply) {
        this.reason = reason;
        this.adminId = adminId;
        this.category = category;
        this.notes = notes;
        this.canReapply = canReapply;
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
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    public Boolean getCanReapply() {
        return canReapply;
    }
    
    public void setCanReapply(Boolean canReapply) {
        this.canReapply = canReapply;
    }
    
    @Override
    public String toString() {
        return "RejectRestaurantRequest{" +
                "reason='" + reason + '\'' +
                ", adminId='" + adminId + '\'' +
                ", category='" + category + '\'' +
                ", notes='" + notes + '\'' +
                ", canReapply=" + canReapply +
                '}';
    }
}