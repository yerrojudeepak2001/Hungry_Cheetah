package com.foodapp.common.ratelimit.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.common.ratelimit.dto.RateLimitRequest;

@FeignClient(name = "RATE-LIMIT-SERVICE", fallback = RateLimitClientFallback.class)
public interface RateLimitClient {
    @PostMapping("/api/ratelimit/check")
    boolean checkRateLimit(@RequestBody RateLimitRequest request);
    
    @PostMapping("/api/ratelimit/increment")
    void incrementCounter(@RequestBody RateLimitRequest request);
    
    @GetMapping("/api/ratelimit/status/{clientId}")
    Map<String, Object> getRateLimitStatus(@PathVariable("clientId") String clientId);
    
    @PutMapping("/api/ratelimit/config/{clientId}")
    void updateRateLimitConfig(@PathVariable("clientId") String clientId,
                             @RequestBody RateLimitConfig config);
}