package com.foodapp.analytics.dto;

import java.time.LocalDateTime;

public class DriverPerformance {
    private String driverId;
    private String name;
    private Integer deliveriesCompleted;
    private Double averageDeliveryTime;
    private Double averageRating;
    private Double totalEarnings;
    private Double efficiency;
    private Integer onTimeDeliveries;
    private Integer lateDeliveries;
    private LocalDateTime lastDelivery;
    private String currentStatus;

    public DriverPerformance() {
    }

    public DriverPerformance(String driverId, String name) {
        this.driverId = driverId;
        this.name = name;
    }

    // Getters and setters
    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDeliveriesCompleted() {
        return deliveriesCompleted;
    }

    public void setDeliveriesCompleted(Integer deliveriesCompleted) {
        this.deliveriesCompleted = deliveriesCompleted;
    }

    public Double getAverageDeliveryTime() {
        return averageDeliveryTime;
    }

    public void setAverageDeliveryTime(Double averageDeliveryTime) {
        this.averageDeliveryTime = averageDeliveryTime;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Double getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(Double efficiency) {
        this.efficiency = efficiency;
    }

    public Integer getOnTimeDeliveries() {
        return onTimeDeliveries;
    }

    public void setOnTimeDeliveries(Integer onTimeDeliveries) {
        this.onTimeDeliveries = onTimeDeliveries;
    }

    public Integer getLateDeliveries() {
        return lateDeliveries;
    }

    public void setLateDeliveries(Integer lateDeliveries) {
        this.lateDeliveries = lateDeliveries;
    }

    public LocalDateTime getLastDelivery() {
        return lastDelivery;
    }

    public void setLastDelivery(LocalDateTime lastDelivery) {
        this.lastDelivery = lastDelivery;
    }

    public String getCurrentStatus() {
        return currentStatus;
    }

    public void setCurrentStatus(String currentStatus) {
        this.currentStatus = currentStatus;
    }
}