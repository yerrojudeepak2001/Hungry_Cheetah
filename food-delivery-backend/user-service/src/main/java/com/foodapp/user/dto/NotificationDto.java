package com.foodapp.user.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private String id;
    
    @NotBlank(message = "Recipient is required")
    private String recipient; // Email, phone, or user ID
    
    @NotBlank(message = "Type is required")
    private String type; // EMAIL, SMS, PUSH
    
    @NotBlank(message = "Title is required")
    private String title;
    
    @NotBlank(message = "Message is required")
    private String message;
    
    private String templateId;
    private String category; // ORDER_UPDATE, PROMOTIONAL, SECURITY, etc.
    
    @NotNull(message = "Priority is required")
    private Integer priority; // 1=High, 2=Medium, 3=Low
    
    private String status; // SENT, PENDING, FAILED
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scheduledAt;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime sentAt;
    
    private Boolean isRead;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime readAt;
    
    private String metadata; // Additional data as JSON string
}