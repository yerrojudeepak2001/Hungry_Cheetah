package com.foodapp.payment.dto;

import lombok.Data;

@Data
public class TaxRates {
    private String region;
    private Double rate;
}