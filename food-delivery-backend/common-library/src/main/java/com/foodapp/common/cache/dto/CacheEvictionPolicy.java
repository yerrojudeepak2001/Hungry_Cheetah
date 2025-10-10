package com.foodapp.common.cache.dto;

public enum CacheEvictionPolicy {
    LRU,    // Least Recently Used
    LFU,    // Least Frequently Used
    FIFO,   // First In First Out
    RANDOM  // Random Eviction
}