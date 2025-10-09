package com.foodapp.common.monitoring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.common.monitoring.dto.LogData;

@FeignClient(name = "LOGGING-CLIENT", fallback = LoggingClientFallback.class)
public interface LoggingClient {
    @PostMapping("/api/logs")
    void recordLog(@RequestBody LogData logData);
    
    @PostMapping("/api/logs/error")
    void recordErrorLog(@RequestBody ErrorLogData errorData);
    
    @PostMapping("/api/logs/audit")
    void recordAuditLog(@RequestBody AuditLogData auditData);
    
    @GetMapping("/api/logs/search")
    List<LogEntry> searchLogs(@RequestParam String query, 
                            @RequestParam LocalDateTime startTime,
                            @RequestParam LocalDateTime endTime);
}