package com.foodapp.admin.service;

import com.foodapp.admin.dto.SystemHealthDTO;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.time.LocalDateTime;

@Service
public class MonitoringService {
    private static final Logger logger = LoggerFactory.getLogger(MonitoringService.class);
    
    public SystemHealthDTO getSystemHealth() {
        SystemHealthDTO health = new SystemHealthDTO();
        health.setServiceStatus("UP");
        health.setDatabaseStatus("UP");
        health.setCacheStatus("UP");
        health.setQueueStatus("UP");
        health.setMemoryUsage(75L);
        health.setCpuUsage(45L);
        health.setDiskUsage(60L);
        health.setLastHealthCheck(LocalDateTime.now());
        health.setHealthy(true);
        return health;
    }
    
    public void clearAllCaches() {
        logger.info("Clearing all caches");
    }
}