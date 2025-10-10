package com.foodapp.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.gateway.dto.ServiceHealth;
import java.util.Map;

@FeignClient(name = "SERVICE-HEALTH", fallback = ServiceHealthClientFallback.class)
public interface ServiceHealthClient {
    @GetMapping("/actuator/health")
    ServiceHealth checkHealth();
    
    @GetMapping("/actuator/metrics")
    Map<String, Object> getMetrics();
    
    @GetMapping("/actuator/info")
    Map<String, Object> getServiceInfo();
}