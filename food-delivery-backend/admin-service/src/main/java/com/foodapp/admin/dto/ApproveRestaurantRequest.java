package com.foodapp.admin.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ApproveRestaurantRequest {
    
    @NotBlank(message = "Approval reason is required")
    @Size(max = 500, message = "Approval reason cannot exceed 500 characters")
    @JsonProperty("reason")
    private String reason;
    
    @JsonProperty("adminId")
    private String adminId;
    
    @JsonProperty("notes")
    private String notes;
    
    // Default constructor
    public ApproveRestaurantRequest() {}
    
    // Constructor
    public ApproveRestaurantRequest(String reason, String adminId, String notes) {
        this.reason = reason;
        this.adminId = adminId;
        this.notes = notes;
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
    
    public String getNotes() {
        return notes;
    }
    
    public void setNotes(String notes) {
        this.notes = notes;
    }
    
    @Override
    public String toString() {
        return "ApproveRestaurantRequest{" +
                "reason='" + reason + '\'' +
                ", adminId='" + adminId + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}