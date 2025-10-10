package com.foodapp.common.cache.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CacheStatistics implements Serializable {
    private String cacheName;
    private long size;
    private long hitCount;
    private long missCount;
    private long evictionCount;
    private double hitRate;
    private double missRate;
    private long averageLoadPenalty;
    private long totalLoadTime;
}