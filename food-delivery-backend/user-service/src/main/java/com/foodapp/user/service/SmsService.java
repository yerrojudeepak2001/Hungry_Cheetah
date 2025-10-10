package com.foodapp.user.service;

public interface SmsService {
    void sendVerificationSms(String to, String verificationCode);
    void sendOrderStatusSms(String to, String orderStatus);
    void sendDeliveryUpdateSms(String to, String updateMessage);
    void sendSecurityAlertSms(String to, String alertMessage);
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