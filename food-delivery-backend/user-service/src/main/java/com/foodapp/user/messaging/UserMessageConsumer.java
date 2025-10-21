package com.foodapp.user.messaging;

import com.foodapp.user.service.UserEmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMessageConsumer {

    private final UserEmailService emailService;

    @JmsListener(destination = "user.welcome")
    public void handleWelcomeMessage(String welcomeMessage) {
        try {
            log.info("Received welcome message: {}", welcomeMessage);
            // Parse message and send welcome email
            // This could be triggered after successful registration
        } catch (Exception e) {
            log.error("Error processing welcome message: {}", welcomeMessage, e);
        }
    }

    @JmsListener(destination = "user.password.changed")
    public void handlePasswordChanged(String passwordChangeMessage) {
        try {
            log.info("Received password changed message: {}", passwordChangeMessage);
            // Send password change confirmation email
        } catch (Exception e) {
            log.error("Error processing password change message: {}", passwordChangeMessage, e);
        }
    }

    @JmsListener(destination = "user.login.suspicious")
    public void handleSuspiciousLogin(String suspiciousLoginMessage) {
        try {
            log.info("Received suspicious login message: {}", suspiciousLoginMessage);
            // Send security alert email for suspicious login attempts
        } catch (Exception e) {
            log.error("Error processing suspicious login message: {}", suspiciousLoginMessage, e);
        }
    }

    @JmsListener(destination = "order.completed")
    public void handleOrderCompleted(String orderCompletedMessage) {
        try {
            log.info("Received order completed message: {}", orderCompletedMessage);
            // Update user loyalty points or send thank you email
        } catch (Exception e) {
            log.error("Error processing order completed message: {}", orderCompletedMessage, e);
        }
    }

    @JmsListener(destination = "user.preferences.update")
    public void handlePreferencesUpdate(String preferencesMessage) {
        try {
            log.info("Received preferences update message: {}", preferencesMessage);
            // Update user preferences cache or sync with other services
        } catch (Exception e) {
            log.error("Error processing preferences update message: {}", preferencesMessage, e);
        }
    }

    @JmsListener(destination = "loyalty.milestone")
    public void handleLoyaltyMilestone(String loyaltyMessage) {
        try {
            log.info("Received loyalty milestone message: {}", loyaltyMessage);
            // Send congratulations email for loyalty milestones
        } catch (Exception e) {
            log.error("Error processing loyalty milestone message: {}", loyaltyMessage, e);
        }
    }

    @JmsListener(destination = "user.birthday")
    public void handleUserBirthday(String birthdayMessage) {
        try {
            log.info("Received user birthday message: {}", birthdayMessage);
            // Send birthday greetings and special offers
        } catch (Exception e) {
            log.error("Error processing user birthday message: {}", birthdayMessage, e);
        }
    }

    @JmsListener(destination = "promotion.targeted")
    public void handleTargetedPromotion(String promotionMessage) {
        try {
            log.info("Received targeted promotion message: {}", promotionMessage);
            // Send personalized promotion emails to users
        } catch (Exception e) {
            log.error("Error processing targeted promotion message: {}", promotionMessage, e);
        }
    }

    @JmsListener(destination = "user.inactivity")
    public void handleUserInactivity(String inactivityMessage) {
        try {
            log.info("Received user inactivity message: {}", inactivityMessage);
            // Send re-engagement emails for inactive users
        } catch (Exception e) {
            log.error("Error processing user inactivity message: {}", inactivityMessage, e);
        }
    }

    @JmsListener(destination = "security.alert")
    public void handleSecurityAlert(String securityMessage) {
        try {
            log.info("Received security alert message: {}", securityMessage);
            // Handle security-related alerts and notifications
        } catch (Exception e) {
            log.error("Error processing security alert message: {}", securityMessage, e);
        }
    }
}