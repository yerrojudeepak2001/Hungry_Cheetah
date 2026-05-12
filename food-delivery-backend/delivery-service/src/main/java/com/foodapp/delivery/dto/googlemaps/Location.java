package com.foodapp.delivery.dto.googlemaps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    
    @JsonProperty("latLng")
    private LatLng latLng;
    
    @JsonProperty("address")
    private String address;
    
    public Location() {}
    
    public Location(double latitude, double longitude) {
        this.latLng = new LatLng(latitude, longitude);
    }
    
    public Location(String address) {
        this.address = address;
    }
    
    public LatLng getLatLng() {
        return latLng;
    }
    
    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public static class LatLng {
        @JsonProperty("latitude")
        private double latitude;
        
        @JsonProperty("longitude")
        private double longitude;
        
        public LatLng() {}
        
        public LatLng(double latitude, double longitude) {
            this.latitude = latitude;
            this.longitude = longitude;
        }
        
        public double getLatitude() {
            return latitude;
        }
        
        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }
        
        public double getLongitude() {
            return longitude;
        }
        
        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}