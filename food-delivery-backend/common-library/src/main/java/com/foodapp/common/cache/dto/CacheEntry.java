package com.foodapp.common.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheEntry<T> implements Serializable {
    private String key;
    private T value;
    private Instant createdAt;
    private Instant lastAccessed;
    private Instant expiresAt;
    private long hitCount;
}