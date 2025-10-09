package com.foodapp.admin.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.admin.dto.ServiceMetrics;

@FeignClient(name = "METRICS-CLIENT", fallback = MetricsClientFallback.class)
public interface MetricsClient {
    @GetMapping("/actuator/metrics")
    ServiceMetrics getServiceMetrics(@RequestParam String serviceName);
    
    @GetMapping("/actuator/loggers")
    Map<String, Object> getLoggerConfiguration();
    
    @PostMapping("/actuator/loggers/{loggerName}")
    void updateLogLevel(@PathVariable("loggerName") String loggerName, 
                       @RequestBody Map<String, String> configuration);
}