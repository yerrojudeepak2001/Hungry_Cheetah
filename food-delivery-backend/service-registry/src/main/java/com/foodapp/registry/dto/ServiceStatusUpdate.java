package com.foodapp.registry.dto;

public class ServiceStatusUpdate {
    private String status;

    public ServiceStatusUpdate() {
    }

    public ServiceStatusUpdate(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}