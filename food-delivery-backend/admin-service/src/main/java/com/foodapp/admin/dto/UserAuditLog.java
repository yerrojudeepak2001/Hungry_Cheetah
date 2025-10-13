package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAuditLog {
    private Long id;
    private Long userId;
    private String action;
    private String details;
    private LocalDateTime timestamp;
    private String ipAddress;
}