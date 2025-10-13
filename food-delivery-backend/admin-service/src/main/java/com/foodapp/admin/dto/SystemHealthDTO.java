package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SystemHealthDTO {
    private String serviceStatus;
    private String databaseStatus;
    private String cacheStatus;
    private String queueStatus;
    private long memoryUsage;
    private long cpuUsage;
    private long diskUsage;
    private LocalDateTime lastHealthCheck;
    private boolean healthy;
}