package com.foodapp.analytics.dto;

import java.time.LocalDateTime;

public class EarningsAnalysis {
    private String driverId;
    private String period;
    private Double totalEarnings;
    private Double baseEarnings;
    private Double tips;
    private Double bonuses;
    private Double incentives;
    private Integer totalDeliveries;
    private Double averageEarningsPerDelivery;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public EarningsAnalysis() {
    }

    public EarningsAnalysis(String driverId, String period) {
        this.driverId = driverId;
        this.period = period;
    }

    // Getters and setters
    public String getDriverId() {
        return driverId;
    }

    public void setDriverId(String driverId) {
        this.driverId = driverId;
    }

    public String getPeriod() {
        return period;
    }

    public void setPeriod(String period) {
        this.period = period;
    }

    public Double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(Double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }

    public Double getBaseEarnings() {
        return baseEarnings;
    }

    public void setBaseEarnings(Double baseEarnings) {
        this.baseEarnings = baseEarnings;
    }

    public Double getTips() {
        return tips;
    }

    public void setTips(Double tips) {
        this.tips = tips;
    }

    public Double getBonuses() {
        return bonuses;
    }

    public void setBonuses(Double bonuses) {
        this.bonuses = bonuses;
    }

    public Double getIncentives() {
        return incentives;
    }

    public void setIncentives(Double incentives) {
        this.incentives = incentives;
    }

    public Integer getTotalDeliveries() {
        return totalDeliveries;
    }

    public void setTotalDeliveries(Integer totalDeliveries) {
        this.totalDeliveries = totalDeliveries;
    }

    public Double getAverageEarningsPerDelivery() {
        return averageEarningsPerDelivery;
    }

    public void setAverageEarningsPerDelivery(Double averageEarningsPerDelivery) {
        this.averageEarningsPerDelivery = averageEarningsPerDelivery;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }
}