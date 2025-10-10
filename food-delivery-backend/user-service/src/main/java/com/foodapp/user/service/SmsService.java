package com.foodapp.user.service;

import org.springframework.stereotype.Service;

@Service
public class SmsService {
    
    public void sendVerificationSms(String phoneNumber, String code) {
        // TODO: Implement SMS sending logic
        System.out.println("Sending verification SMS to: " + phoneNumber + " with code: " + code);
    }
    
    public void sendOrderUpdateSms(String phoneNumber, String message) {
        // TODO: Implement order update SMS
        System.out.println("Sending order update SMS to: " + phoneNumber);
    }
}