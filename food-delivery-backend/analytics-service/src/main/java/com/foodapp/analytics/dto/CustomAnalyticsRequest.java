package com.foodapp.analytics.dto;

import java.util.List;
import java.util.Map;

public class CustomAnalyticsRequest {
    private String query;
    private List<String> dimensions;
    private List<String> metrics;
    private Map<String, Object> filters;
    private Map<String, Object> aggregations;
    private String timeframe;
    private Integer limit;

    public CustomAnalyticsRequest() {
    }

    public CustomAnalyticsRequest(String query, List<String> dimensions, List<String> metrics) {
        this.query = query;
        this.dimensions = dimensions;
        this.metrics = metrics;
    }

    // Getters and setters
    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getDimensions() {
        return dimensions;
    }

    public void setDimensions(List<String> dimensions) {
        this.dimensions = dimensions;
    }

    public List<String> getMetrics() {
        return metrics;
    }

    public void setMetrics(List<String> metrics) {
        this.metrics = metrics;
    }

    public Map<String, Object> getFilters() {
        return filters;
    }

    public void setFilters(Map<String, Object> filters) {
        this.filters = filters;
    }

    public Map<String, Object> getAggregations() {
        return aggregations;
    }

    public void setAggregations(Map<String, Object> aggregations) {
        this.aggregations = aggregations;
    }

    public String getTimeframe() {
        return timeframe;
    }

    public void setTimeframe(String timeframe) {
        this.timeframe = timeframe;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
}