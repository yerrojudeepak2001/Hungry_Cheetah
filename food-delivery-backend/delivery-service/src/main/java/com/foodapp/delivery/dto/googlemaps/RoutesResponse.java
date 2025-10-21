package com.foodapp.delivery.dto.googlemaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RoutesResponse {
    
    @JsonProperty("routes")
    private List<Route> routes;
    
    public List<Route> getRoutes() {
        return routes;
    }
    
    public void setRoutes(List<Route> routes) {
        this.routes = routes;
    }
    
    public static class Route {
        @JsonProperty("legs")
        private List<RouteLeg> legs;
        
        @JsonProperty("distanceMeters")
        private int distanceMeters;
        
        @JsonProperty("duration")
        private String duration;
        
        @JsonProperty("staticDuration")
        private String staticDuration;
        
        @JsonProperty("polyline")
        private Polyline polyline;
        
        @JsonProperty("description")
        private String description;
        
        @JsonProperty("warnings")
        private List<String> warnings;
        
        @JsonProperty("viewport")
        private Viewport viewport;
        
        public List<RouteLeg> getLegs() {
            return legs;
        }
        
        public void setLegs(List<RouteLeg> legs) {
            this.legs = legs;
        }
        
        public int getDistanceMeters() {
            return distanceMeters;
        }
        
        public void setDistanceMeters(int distanceMeters) {
            this.distanceMeters = distanceMeters;
        }
        
        public String getDuration() {
            return duration;
        }
        
        public void setDuration(String duration) {
            this.duration = duration;
        }
        
        public String getStaticDuration() {
            return staticDuration;
        }
        
        public void setStaticDuration(String staticDuration) {
            this.staticDuration = staticDuration;
        }
        
        public Polyline getPolyline() {
            return polyline;
        }
        
        public void setPolyline(Polyline polyline) {
            this.polyline = polyline;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public List<String> getWarnings() {
            return warnings;
        }
        
        public void setWarnings(List<String> warnings) {
            this.warnings = warnings;
        }
        
        public Viewport getViewport() {
            return viewport;
        }
        
        public void setViewport(Viewport viewport) {
            this.viewport = viewport;
        }
    }
    
    public static class RouteLeg {
        @JsonProperty("distanceMeters")
        private int distanceMeters;
        
        @JsonProperty("duration")
        private String duration;
        
        @JsonProperty("staticDuration")
        private String staticDuration;
        
        @JsonProperty("polyline")
        private Polyline polyline;
        
        @JsonProperty("startLocation")
        private Location startLocation;
        
        @JsonProperty("endLocation")
        private Location endLocation;
        
        @JsonProperty("steps")
        private List<RouteStep> steps;
        
        public int getDistanceMeters() {
            return distanceMeters;
        }
        
        public void setDistanceMeters(int distanceMeters) {
            this.distanceMeters = distanceMeters;
        }
        
        public String getDuration() {
            return duration;
        }
        
        public void setDuration(String duration) {
            this.duration = duration;
        }
        
        public String getStaticDuration() {
            return staticDuration;
        }
        
        public void setStaticDuration(String staticDuration) {
            this.staticDuration = staticDuration;
        }
        
        public Polyline getPolyline() {
            return polyline;
        }
        
        public void setPolyline(Polyline polyline) {
            this.polyline = polyline;
        }
        
        public Location getStartLocation() {
            return startLocation;
        }
        
        public void setStartLocation(Location startLocation) {
            this.startLocation = startLocation;
        }
        
        public Location getEndLocation() {
            return endLocation;
        }
        
        public void setEndLocation(Location endLocation) {
            this.endLocation = endLocation;
        }
        
        public List<RouteStep> getSteps() {
            return steps;
        }
        
        public void setSteps(List<RouteStep> steps) {
            this.steps = steps;
        }
    }
    
    public static class RouteStep {
        @JsonProperty("distanceMeters")
        private int distanceMeters;
        
        @JsonProperty("staticDuration")
        private String staticDuration;
        
        @JsonProperty("polyline")
        private Polyline polyline;
        
        @JsonProperty("startLocation")
        private Location startLocation;
        
        @JsonProperty("endLocation")
        private Location endLocation;
        
        @JsonProperty("navigationInstruction")
        private NavigationInstruction navigationInstruction;
        
        public int getDistanceMeters() {
            return distanceMeters;
        }
        
        public void setDistanceMeters(int distanceMeters) {
            this.distanceMeters = distanceMeters;
        }
        
        public String getStaticDuration() {
            return staticDuration;
        }
        
        public void setStaticDuration(String staticDuration) {
            this.staticDuration = staticDuration;
        }
        
        public Polyline getPolyline() {
            return polyline;
        }
        
        public void setPolyline(Polyline polyline) {
            this.polyline = polyline;
        }
        
        public Location getStartLocation() {
            return startLocation;
        }
        
        public void setStartLocation(Location startLocation) {
            this.startLocation = startLocation;
        }
        
        public Location getEndLocation() {
            return endLocation;
        }
        
        public void setEndLocation(Location endLocation) {
            this.endLocation = endLocation;
        }
        
        public NavigationInstruction getNavigationInstruction() {
            return navigationInstruction;
        }
        
        public void setNavigationInstruction(NavigationInstruction navigationInstruction) {
            this.navigationInstruction = navigationInstruction;
        }
    }
    
    public static class Polyline {
        @JsonProperty("encodedPolyline")
        private String encodedPolyline;
        
        public String getEncodedPolyline() {
            return encodedPolyline;
        }
        
        public void setEncodedPolyline(String encodedPolyline) {
            this.encodedPolyline = encodedPolyline;
        }
    }
    
    public static class NavigationInstruction {
        @JsonProperty("maneuver")
        private String maneuver;
        
        @JsonProperty("instructions")
        private String instructions;
        
        public String getManeuver() {
            return maneuver;
        }
        
        public void setManeuver(String maneuver) {
            this.maneuver = maneuver;
        }
        
        public String getInstructions() {
            return instructions;
        }
        
        public void setInstructions(String instructions) {
            this.instructions = instructions;
        }
    }
    
    public static class Viewport {
        @JsonProperty("low")
        private Location.LatLng low;
        
        @JsonProperty("high")
        private Location.LatLng high;
        
        public Location.LatLng getLow() {
            return low;
        }
        
        public void setLow(Location.LatLng low) {
            this.low = low;
        }
        
        public Location.LatLng getHigh() {
            return high;
        }
        
        public void setHigh(Location.LatLng high) {
            this.high = high;
        }
    }
}