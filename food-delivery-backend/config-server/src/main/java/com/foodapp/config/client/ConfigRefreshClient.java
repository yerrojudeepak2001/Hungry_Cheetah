package com.foodapp.config.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@FeignClient(name = "CONFIG-REFRESH-CLIENT")
public interface ConfigRefreshClient {
    @PostMapping("/actuator/refresh")
    void refreshConfiguration();
    
    @GetMapping("/actuator/env")
    Map<String, Object> getEnvironment();
    
    @PostMapping("/actuator/bus-refresh")
    void refreshConfigurationBus();
}