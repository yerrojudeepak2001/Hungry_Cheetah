package com.foodapp.registry.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name = "HEALTH-CHECK-CLIENT")
public interface HealthCheckClient {
    @GetMapping("/actuator/health")
    Map<String, Object> checkServiceHealth();
    
    @GetMapping("/actuator/info")
    Map<String, Object> getServiceInfo();
    
    @PostMapping("/actuator/serviceregistry")
    void updateServiceStatus(@RequestBody Map<String, Object> status);
}