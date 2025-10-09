package com.foodapp.notification.service;

import com.foodapp.common.constants.AppConstants;
import com.foodapp.common.dto.OrderDTO;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {
    private final TwilioService twilioService;
    private final EmailService emailService;

    public NotificationService(TwilioService twilioService, EmailService emailService) {
        this.twilioService = twilioService;
        this.emailService = emailService;
    }

    @JmsListener(destination = AppConstants.ORDER_CREATED_QUEUE)
    public void handleOrderCreated(OrderDTO order) {
        // Send SMS notification
        twilioService.sendSMS(order.getUserId(), 
            "Your order #" + order.getId() + " has been confirmed!");
        
        // Send email notification
        emailService.sendOrderConfirmation(order);
    }

    @JmsListener(destination = AppConstants.DELIVERY_COMPLETED_QUEUE)
    public void handleDeliveryCompleted(OrderDTO order) {
        // Send delivery completion notification
        twilioService.sendSMS(order.getUserId(), 
            "Your order #" + order.getId() + " has been delivered!");
        
        // Send email for feedback
        emailService.sendFeedbackRequest(order);
    }
}