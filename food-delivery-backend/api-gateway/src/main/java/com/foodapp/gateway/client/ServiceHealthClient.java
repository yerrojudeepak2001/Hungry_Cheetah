package com.foodapp.gateway.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name = "SERVICE-HEALTH")
public interface ServiceHealthClient {
    @GetMapping("/actuator/health")
    Map<String, Object> checkHealth();
    
    @GetMapping("/actuator/metrics")
    Map<String, Object> getMetrics();
    
    @GetMapping("/actuator/info")
    Map<String, Object> getServiceInfo();
}