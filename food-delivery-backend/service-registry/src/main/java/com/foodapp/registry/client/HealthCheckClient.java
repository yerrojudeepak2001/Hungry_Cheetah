package com.foodapp.registry.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.registry.dto.ServiceHealth;

@FeignClient(name = "HEALTH-CHECK-CLIENT", fallback = HealthCheckClientFallback.class)
public interface HealthCheckClient {
    @GetMapping("/actuator/health")
    ServiceHealth checkServiceHealth();
    
    @GetMapping("/actuator/info")
    Map<String, Object> getServiceInfo();
    
    @PostMapping("/actuator/serviceregistry")
    void updateServiceStatus(@RequestBody ServiceStatusUpdate status);
}