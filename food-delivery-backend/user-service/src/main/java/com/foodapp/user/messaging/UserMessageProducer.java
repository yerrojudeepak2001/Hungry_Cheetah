package com.foodapp.user.messaging;

import com.foodapp.user.config.JmsConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMessageProducer {

    private final JmsTemplate jmsTemplate;

    public void sendUserRegisteredMessage(String userId, String email, String firstName, String lastName, 
                                        String phoneNumber, String registrationMethod, Map<String, Object> metadata) {
        try {
            UserRegisteredMessage message = UserRegisteredMessage.builder()
                    .userId(userId)
                    .email(email)
                    .firstName(firstName)
                    .lastName(lastName)
                    .phoneNumber(phoneNumber)
                    .registrationMethod(registrationMethod)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.USER_REGISTERED_QUEUE, message);
            log.info("User registered message sent for user: {} with email: {}", userId, email);
        } catch (Exception e) {
            log.error("Error sending user registered message for user: {}", userId, e);
        }
    }

    public void sendUserProfileUpdatedMessage(String userId, String email, String updateType, 
                                            Map<String, Object> oldValues, Map<String, Object> newValues, String updatedBy) {
        try {
            UserProfileUpdatedMessage message = UserProfileUpdatedMessage.builder()
                    .userId(userId)
                    .email(email)
                    .updateType(updateType)
                    .oldValues(oldValues)
                    .newValues(newValues)
                    .updatedBy(updatedBy)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.USER_PROFILE_UPDATED_QUEUE, message);
            log.info("User profile updated message sent for user: {} - update type: {}", userId, updateType);
        } catch (Exception e) {
            log.error("Error sending user profile updated message for user: {}", userId, e);
        }
    }

    public void sendUserLoginMessage(String userId, String email, String loginMethod, String ipAddress, 
                                   String userAgent, String deviceType, String location, boolean successful, 
                                   String failureReason, Map<String, Object> metadata) {
        try {
            UserLoginMessage message = UserLoginMessage.builder()
                    .userId(userId)
                    .email(email)
                    .loginMethod(loginMethod)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .deviceType(deviceType)
                    .location(location)
                    .successful(successful)
                    .failureReason(failureReason)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.USER_LOGIN_QUEUE, message);
            log.info("User login message sent for user: {} - success: {}", userId, successful);
        } catch (Exception e) {
            log.error("Error sending user login message for user: {}", userId, e);
        }
    }

    public void sendUserPasswordResetMessage(String userId, String email, String resetToken, String requestMethod, 
                                           String ipAddress, String userAgent, long expiresAt) {
        try {
            UserPasswordResetMessage message = UserPasswordResetMessage.builder()
                    .userId(userId)
                    .email(email)
                    .resetToken(resetToken)
                    .requestMethod(requestMethod)
                    .ipAddress(ipAddress)
                    .userAgent(userAgent)
                    .expiresAt(expiresAt)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.USER_PASSWORD_RESET_QUEUE, message);
            log.info("User password reset message sent for user: {}", userId);
        } catch (Exception e) {
            log.error("Error sending user password reset message for user: {}", userId, e);
        }
    }

    public void sendUserEmailVerificationMessage(String userId, String email, String verificationToken, 
                                               String verificationType, long expiresAt) {
        try {
            UserEmailVerificationMessage message = UserEmailVerificationMessage.builder()
                    .userId(userId)
                    .email(email)
                    .verificationToken(verificationToken)
                    .verificationType(verificationType)
                    .expiresAt(expiresAt)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.USER_EMAIL_VERIFICATION_QUEUE, message);
            log.info("User email verification message sent for user: {} - type: {}", userId, verificationType);
        } catch (Exception e) {
            log.error("Error sending user email verification message for user: {}", userId, e);
        }
    }

    public void sendUserAccountStatusMessage(String userId, String email, String oldStatus, String newStatus, 
                                           String reason, String changedBy, String adminUserId, Map<String, Object> metadata) {
        try {
            UserAccountStatusMessage message = UserAccountStatusMessage.builder()
                    .userId(userId)
                    .email(email)
                    .oldStatus(oldStatus)
                    .newStatus(newStatus)
                    .reason(reason)
                    .changedBy(changedBy)
                    .adminUserId(adminUserId)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.USER_ACCOUNT_STATUS_QUEUE, message);
            log.info("User account status message sent for user: {} - status changed from {} to {}", userId, oldStatus, newStatus);
        } catch (Exception e) {
            log.error("Error sending user account status message for user: {}", userId, e);
        }
    }

    public void sendNotificationMessage(String type, String recipient, String subject, String content, Map<String, Object> metadata) {
        try {
            NotificationMessage message = NotificationMessage.builder()
                    .type(type)
                    .recipient(recipient)
                    .subject(subject)
                    .content(content)
                    .metadata(metadata)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.NOTIFICATION_QUEUE, message);
            log.info("Notification message sent: {} to {}", type, recipient);
        } catch (Exception e) {
            log.error("Error sending notification message to: {}", recipient, e);
        }
    }

    public void sendAnalyticsMessage(String eventType, String userId, Map<String, Object> eventData) {
        try {
            UserAnalyticsMessage message = UserAnalyticsMessage.builder()
                    .eventType(eventType)
                    .userId(userId)
                    .eventData(eventData)
                    .timestamp(System.currentTimeMillis())
                    .build();

            jmsTemplate.convertAndSend(JmsConfig.ANALYTICS_QUEUE, message);
            log.info("Analytics message sent: {} for user: {}", eventType, userId);
        } catch (Exception e) {
            log.error("Error sending analytics message for user: {}", userId, e);
        }
    }
}