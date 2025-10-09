package com.foodapp.config.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.config.dto.ServiceConfig;

@FeignClient(name = "CONFIG-REFRESH-CLIENT", fallback = ConfigRefreshClientFallback.class)
public interface ConfigRefreshClient {
    @PostMapping("/actuator/refresh")
    void refreshConfiguration();
    
    @GetMapping("/actuator/env")
    Map<String, Object> getEnvironment();
    
    @PostMapping("/actuator/bus-refresh")
    void refreshConfigurationBus();
}