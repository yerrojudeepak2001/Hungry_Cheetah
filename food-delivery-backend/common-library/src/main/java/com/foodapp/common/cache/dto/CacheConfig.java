package com.foodapp.common.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Duration;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheConfig implements Serializable {
    private String cacheName;
    private Duration ttl;
    private Duration idleTime;
    private long maxSize;
    private CacheEvictionPolicy evictionPolicy;
    private boolean enableStatistics;
    private boolean persistToDatabase;
}