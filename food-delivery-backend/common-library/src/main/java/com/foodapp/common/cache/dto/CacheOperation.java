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
public class CacheOperation implements Serializable {
    private Object value;
    private Duration ttl;
    private String region;
    private CacheEvictionPolicy evictionPolicy;
    private boolean async;
}