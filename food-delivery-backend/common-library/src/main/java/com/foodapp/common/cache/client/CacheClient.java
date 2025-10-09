package com.foodapp.common.cache.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.common.cache.dto.CacheOperation;

@FeignClient(name = "CACHE-SERVICE", fallback = CacheClientFallback.class)
public interface CacheClient {
    @GetMapping("/api/cache/{key}")
    Object getCachedValue(@PathVariable("key") String key);
    
    @PutMapping("/api/cache/{key}")
    void setCachedValue(@PathVariable("key") String key, 
                       @RequestBody CacheOperation operation);
    
    @DeleteMapping("/api/cache/{key}")
    void invalidateCache(@PathVariable("key") String key);
    
    @PostMapping("/api/cache/pattern/{pattern}")
    void invalidateByPattern(@PathVariable("pattern") String pattern);
    
    @GetMapping("/api/cache/stats")
    Map<String, Object> getCacheStatistics();
}