package com.foodapp.delivery.dto.googlemaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class DistanceMatrixResponse {
    
    @JsonProperty("destination_addresses")
    private List<String> destinationAddresses;
    
    @JsonProperty("origin_addresses")
    private List<String> originAddresses;
    
    @JsonProperty("rows")
    private List<Row> rows;
    
    @JsonProperty("status")
    private String status;
    
    public List<String> getDestinationAddresses() {
        return destinationAddresses;
    }
    
    public void setDestinationAddresses(List<String> destinationAddresses) {
        this.destinationAddresses = destinationAddresses;
    }
    
    public List<String> getOriginAddresses() {
        return originAddresses;
    }
    
    public void setOriginAddresses(List<String> originAddresses) {
        this.originAddresses = originAddresses;
    }
    
    public List<Row> getRows() {
        return rows;
    }
    
    public void setRows(List<Row> rows) {
        this.rows = rows;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public static class Row {
        @JsonProperty("elements")
        private List<Element> elements;
        
        public List<Element> getElements() {
            return elements;
        }
        
        public void setElements(List<Element> elements) {
            this.elements = elements;
        }
    }
    
    public static class Element {
        @JsonProperty("distance")
        private Distance distance;
        
        @JsonProperty("duration")
        private Duration duration;
        
        @JsonProperty("duration_in_traffic")
        private Duration durationInTraffic;
        
        @JsonProperty("status")
        private String status;
        
        public Distance getDistance() {
            return distance;
        }
        
        public void setDistance(Distance distance) {
            this.distance = distance;
        }
        
        public Duration getDuration() {
            return duration;
        }
        
        public void setDuration(Duration duration) {
            this.duration = duration;
        }
        
        public Duration getDurationInTraffic() {
            return durationInTraffic;
        }
        
        public void setDurationInTraffic(Duration durationInTraffic) {
            this.durationInTraffic = durationInTraffic;
        }
        
        public String getStatus() {
            return status;
        }
        
        public void setStatus(String status) {
            this.status = status;
        }
    }
    
    public static class Distance {
        @JsonProperty("text")
        private String text;
        
        @JsonProperty("value")
        private int value; // in meters
        
        public String getText() {
            return text;
        }
        
        public void setText(String text) {
            this.text = text;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }
    
    public static class Duration {
        @JsonProperty("text")
        private String text;
        
        @JsonProperty("value")
        private int value; // in seconds
        
        public String getText() {
            return text;
        }
        
        public void setText(String text) {
            this.text = text;
        }
        
        public int getValue() {
            return value;
        }
        
        public void setValue(int value) {
            this.value = value;
        }
    }
}