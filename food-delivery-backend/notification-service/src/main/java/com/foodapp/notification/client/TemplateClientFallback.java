package com.foodapp.notification.client;

import com.foodapp.notification.model.NotificationTemplate;
import com.foodapp.notification.dto.TemplateRenderRequest;
import com.foodapp.notification.dto.ValidationResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class TemplateClientFallback implements TemplateClient {
    
    @Override
    public NotificationTemplate getTemplate(String templateId, String locale) {
        log.warn("TemplateClient fallback: getTemplate called for template {} with locale {}", templateId, locale);
        return NotificationTemplate.builder()
            .id(templateId)
            .name("Fallback Template")
            .titleTemplate("Notification")
            .messageTemplate("Default notification message")
            .isActive(true)
            .createdAt(LocalDateTime.now())
            .updatedAt(LocalDateTime.now())
            .build();
    }
    
    @Override
    public List<NotificationTemplate> getTemplatesByType(String type) {
        log.warn("TemplateClient fallback: getTemplatesByType called for type {}", type);
        return Collections.emptyList();
    }
    
    @Override
    public String renderTemplate(TemplateRenderRequest request) {
        log.warn("TemplateClient fallback: renderTemplate called for template {}", request.getTemplateId());
        return "Default rendered template content";
    }
    
    @Override
    public ValidationResult validateTemplate(String templateId, Map<String, Object> parameters) {
        log.warn("TemplateClient fallback: validateTemplate called for template {}", templateId);
        return ValidationResult.builder()
            .isValid(true)
            .errors(Collections.emptyList())
            .warnings(Collections.emptyList())
            .build();
    }
}