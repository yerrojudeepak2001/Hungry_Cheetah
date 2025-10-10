package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuditLogDTO {
    private Long id;
    private String action;
    private String performedBy;
    private String entityType;
    private String entityId;
    private String details;
    private LocalDateTime timestamp;
    private String ipAddress;
}