package com.foodapp.payment.dto.stripe;

import lombok.Data;

@Data
public class CreateCustomerRequest {
    private String email;
    private String name;
    private String phone;
    private String address;
}