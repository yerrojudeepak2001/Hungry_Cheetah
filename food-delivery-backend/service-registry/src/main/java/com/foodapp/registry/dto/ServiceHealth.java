package com.foodapp.registry.dto;

public class ServiceHealth {
    private String status;
    private Object details;

    public ServiceHealth() {
    }

    public ServiceHealth(String status, Object details) {
        this.status = status;
        this.details = details;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getDetails() {
        return details;
    }

    public void setDetails(Object details) {
        this.details = details;
    }
}