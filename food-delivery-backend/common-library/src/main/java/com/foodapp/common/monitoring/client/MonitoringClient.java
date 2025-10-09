package com.foodapp.common.monitoring.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.common.monitoring.dto.MetricsData;

@FeignClient(name = "MONITORING-CLIENT", fallback = MonitoringClientFallback.class)
public interface MonitoringClient {
    @PostMapping("/actuator/metrics/custom/{metricName}")
    void recordMetric(@PathVariable("metricName") String metricName, 
                     @RequestBody MetricsData data);
    
    @GetMapping("/actuator/health/custom")
    HealthStatus getCustomHealthStatus();
    
    @PostMapping("/actuator/events")
    void recordEvent(@RequestBody EventData event);
}