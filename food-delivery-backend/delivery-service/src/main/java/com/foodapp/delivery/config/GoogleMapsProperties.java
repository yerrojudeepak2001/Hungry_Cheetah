package com.foodapp.delivery.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "google.maps")
public class GoogleMapsProperties {
    
    private String apiKey;
    private Routes routes = new Routes();
    private DistanceMatrix distanceMatrix = new DistanceMatrix();
    
    public String getApiKey() {
        return apiKey;
    }
    
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }
    
    public Routes getRoutes() {
        return routes;
    }
    
    public void setRoutes(Routes routes) {
        this.routes = routes;
    }
    
    public DistanceMatrix getDistanceMatrix() {
        return distanceMatrix;
    }
    
    public void setDistanceMatrix(DistanceMatrix distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }
    
    public static class Routes {
        private String url;
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
    }
    
    public static class DistanceMatrix {
        private String url;
        
        public String getUrl() {
            return url;
        }
        
        public void setUrl(String url) {
            this.url = url;
        }
    }
}