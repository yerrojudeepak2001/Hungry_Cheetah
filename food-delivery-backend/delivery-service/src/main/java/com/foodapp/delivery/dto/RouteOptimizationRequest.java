package com.foodapp.delivery.dto;

import com.foodapp.delivery.model.Delivery;
import java.util.List;

public class RouteOptimizationRequest {
    private Long partnerId;
    private List<Delivery> deliveries;
    
    public RouteOptimizationRequest() {}
    
    public RouteOptimizationRequest(Long partnerId, List<Delivery> deliveries) {
        this.partnerId = partnerId;
        this.deliveries = deliveries;
    }
    
    public Long getPartnerId() {
        return partnerId;
    }
    
    public void setPartnerId(Long partnerId) {
        this.partnerId = partnerId;
    }
    
    public List<Delivery> getDeliveries() {
        return deliveries;
    }
    
    public void setDeliveries(List<Delivery> deliveries) {
        this.deliveries = deliveries;
    }
}