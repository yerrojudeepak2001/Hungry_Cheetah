package com.foodapp.user.service;

public interface SmsService {
    void sendVerificationSms(String to, String verificationCode);
    void sendOrderStatusSms(String to, String orderStatus);
    void sendDeliveryUpdateSms(String to, String updateMessage);
    void sendSecurityAlertSms(String to, String alertMessage);
}