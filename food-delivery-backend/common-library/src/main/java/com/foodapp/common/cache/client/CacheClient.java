package com.foodapp.common.cache.client;

import com.foodapp.common.cache.dto.CacheOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Feign client interface for interacting with the cache service.
 * Provides methods for cache operations with fallback support.
 */
@FeignClient(name = "CACHE-SERVICE", fallback = CacheClientFallback.class)
public interface CacheClient {
    /**
     * Retrieves a cached value for the given key.
     *
     * @param key The cache key
     * @return The cached value or null if not found
     */
    @GetMapping("/api/cache/{key}")
    Object getCachedValue(@PathVariable("key") String key);
    
    /**
     * Sets a value in the cache with the specified operation parameters.
     *
     * @param key The cache key
     * @param operation The cache operation details including value and TTL
     */
    @PutMapping("/api/cache/{key}")
    void setCachedValue(@PathVariable("key") String key, 
                       @RequestBody CacheOperation operation);
    
    /**
     * Invalidates (removes) a specific key from the cache.
     *
     * @param key The cache key to invalidate
     */
    @DeleteMapping("/api/cache/{key}")
    void invalidateCache(@PathVariable("key") String key);
    
    /**
     * Invalidates all cache entries matching the given pattern.
     *
     * @param pattern The pattern to match cache keys against
     */
    @PostMapping("/api/cache/pattern/{pattern}")
    void invalidateByPattern(@PathVariable("pattern") String pattern);
    
    /**
     * Retrieves cache statistics including hits, misses, and size.
     *
     * @return Map of statistics
     */
    @GetMapping("/api/cache/stats")
    Map<String, Object> getCacheStatistics();
}