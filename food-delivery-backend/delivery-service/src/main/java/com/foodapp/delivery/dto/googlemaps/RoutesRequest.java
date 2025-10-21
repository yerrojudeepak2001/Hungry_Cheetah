package com.foodapp.delivery.dto.googlemaps;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class RoutesRequest {
    
    @JsonProperty("origin")
    private Waypoint origin;
    
    @JsonProperty("destination")
    private Waypoint destination;
    
    @JsonProperty("intermediates")
    private List<Waypoint> intermediates;
    
    @JsonProperty("travelMode")
    private String travelMode = "DRIVE";
    
    @JsonProperty("routingPreference")
    private String routingPreference = "TRAFFIC_AWARE";
    
    @JsonProperty("computeAlternativeRoutes")
    private boolean computeAlternativeRoutes = false;
    
    @JsonProperty("routeModifiers")
    private RouteModifiers routeModifiers;
    
    @JsonProperty("languageCode")
    private String languageCode = "en-US";
    
    @JsonProperty("units")
    private String units = "METRIC";
    
    public RoutesRequest() {}
    
    public RoutesRequest(Waypoint origin, Waypoint destination) {
        this.origin = origin;
        this.destination = destination;
    }
    
    // Getters and Setters
    public Waypoint getOrigin() {
        return origin;
    }
    
    public void setOrigin(Waypoint origin) {
        this.origin = origin;
    }
    
    public Waypoint getDestination() {
        return destination;
    }
    
    public void setDestination(Waypoint destination) {
        this.destination = destination;
    }
    
    public List<Waypoint> getIntermediates() {
        return intermediates;
    }
    
    public void setIntermediates(List<Waypoint> intermediates) {
        this.intermediates = intermediates;
    }
    
    public String getTravelMode() {
        return travelMode;
    }
    
    public void setTravelMode(String travelMode) {
        this.travelMode = travelMode;
    }
    
    public String getRoutingPreference() {
        return routingPreference;
    }
    
    public void setRoutingPreference(String routingPreference) {
        this.routingPreference = routingPreference;
    }
    
    public boolean isComputeAlternativeRoutes() {
        return computeAlternativeRoutes;
    }
    
    public void setComputeAlternativeRoutes(boolean computeAlternativeRoutes) {
        this.computeAlternativeRoutes = computeAlternativeRoutes;
    }
    
    public RouteModifiers getRouteModifiers() {
        return routeModifiers;
    }
    
    public void setRouteModifiers(RouteModifiers routeModifiers) {
        this.routeModifiers = routeModifiers;
    }
    
    public String getLanguageCode() {
        return languageCode;
    }
    
    public void setLanguageCode(String languageCode) {
        this.languageCode = languageCode;
    }
    
    public String getUnits() {
        return units;
    }
    
    public void setUnits(String units) {
        this.units = units;
    }
    
    public static class Waypoint {
        @JsonProperty("location")
        private Location location;
        
        @JsonProperty("via")
        private boolean via = false;
        
        public Waypoint() {}
        
        public Waypoint(Location location) {
            this.location = location;
        }
        
        public Location getLocation() {
            return location;
        }
        
        public void setLocation(Location location) {
            this.location = location;
        }
        
        public boolean isVia() {
            return via;
        }
        
        public void setVia(boolean via) {
            this.via = via;
        }
    }
    
    public static class RouteModifiers {
        @JsonProperty("avoidTolls")
        private boolean avoidTolls = false;
        
        @JsonProperty("avoidHighways")
        private boolean avoidHighways = false;
        
        @JsonProperty("avoidFerries")
        private boolean avoidFerries = false;
        
        @JsonProperty("avoidIndoor")
        private boolean avoidIndoor = false;
        
        // Getters and Setters
        public boolean isAvoidTolls() {
            return avoidTolls;
        }
        
        public void setAvoidTolls(boolean avoidTolls) {
            this.avoidTolls = avoidTolls;
        }
        
        public boolean isAvoidHighways() {
            return avoidHighways;
        }
        
        public void setAvoidHighways(boolean avoidHighways) {
            this.avoidHighways = avoidHighways;
        }
        
        public boolean isAvoidFerries() {
            return avoidFerries;
        }
        
        public void setAvoidFerries(boolean avoidFerries) {
            this.avoidFerries = avoidFerries;
        }
        
        public boolean isAvoidIndoor() {
            return avoidIndoor;
        }
        
        public void setAvoidIndoor(boolean avoidIndoor) {
            this.avoidIndoor = avoidIndoor;
        }
    }
}