package com.foodapp.analytics.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class AnalyticsResponse {
    private String type;
    private LocalDateTime timestamp;
    private Map<String, Object> data;
    private Map<String, Object> metrics;
    private String period;
    private Integer totalRecords;

    public AnalyticsResponse() {
    }

    public AnalyticsResponse(String type, Map<String, Object> data) {
        this.type = type;
        this.data = data;
        this.timestamp = LocalDateTime.now();
    }

    // Getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public Map<String, Object> getMetrics() {
        return metrics;
    }

    public void setMetrics(Map<String, Object> metrics) {
        this.metrics = metrics;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Integer getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(Integer totalRecords) {
        this.totalRecords = totalRecords;
    }
}