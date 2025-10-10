package com.foodapp.config.dto;

import java.util.Map;

public class ServiceConfig {
    private String serviceName;
    private Map<String, Object> properties;

    public ServiceConfig() {
    }

    public ServiceConfig(String serviceName, Map<String, Object> properties) {
        this.serviceName = serviceName;
        this.properties = properties;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map<String, Object> properties) {
        this.properties = properties;
    }
}