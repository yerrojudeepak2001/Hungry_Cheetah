package com.foodapp.analytics.dto;

import java.util.Map;

public class Row {
    private Map<String, Object> data;
    private String id;
    private Long timestamp;

    public Row() {
    }

    public Row(Map<String, Object> data) {
        this.data = data;
    }

    public Row(String id, Map<String, Object> data) {
        this.id = id;
        this.data = data;
    }

    // Getters and setters
    public Map<String, Object> getData() {
        return data;
    }

    public void setData(Map<String, Object> data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}