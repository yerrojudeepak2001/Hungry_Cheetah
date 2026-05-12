package com.foodapp.analytics.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class TrendAnalysis {
    private String metric;
    private String period;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private List<Map<String, Object>> dataPoints;
    private String trend;
    private Double growthRate;
    private Map<String, Object> insights;

    public TrendAnalysis() {
    }

    public TrendAnalysis(String metric, String period) {
        this.metric = metric;
        this.period = period;
    }

    // Getters and setters
    public String getMetric() {
        return metric;
    }

    public void setMetric(String metric) {
        this.metric = metric;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public List<Map<String, Object>> getDataPoints() {
        return dataPoints;
    }

    public void setDataPoints(List<Map<String, Object>> dataPoints) {
        this.dataPoints = dataPoints;
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

    public Double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(Double growthRate) {
        this.growthRate = growthRate;
    }

    public Map<String, Object> getInsights() {
        return insights;
    }

    public void setInsights(Map<String, Object> insights) {
        this.insights = insights;
    }
}