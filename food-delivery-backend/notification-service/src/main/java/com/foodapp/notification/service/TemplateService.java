package com.foodapp.notification.service;

import com.foodapp.notification.model.NotificationTemplate;
import com.foodapp.notification.model.NotificationChannel;
import com.foodapp.notification.repository.NotificationTemplateRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

@Service
@RequiredArgsConstructor
@Slf4j
public class TemplateService {
    
    private final NotificationTemplateRepository templateRepository;
    private static final Pattern VARIABLE_PATTERN = Pattern.compile("\\{\\{(\\w+)\\}\\}");
    
    @Transactional
    public NotificationTemplate createTemplate(NotificationTemplate template) {
        try {
            template.setCreatedAt(LocalDateTime.now());
            template.setUpdatedAt(LocalDateTime.now());
            
            NotificationTemplate saved = templateRepository.save(template);
            log.info("Created notification template: {}", saved.getId());
            return saved;
            
        } catch (Exception e) {
            log.error("Error creating notification template: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to create notification template", e);
        }
    }
    
    @Transactional
    public NotificationTemplate updateTemplate(String templateId, NotificationTemplate updates) {
        try {
            Optional<NotificationTemplate> existingOpt = templateRepository.findById(templateId);
            if (existingOpt.isEmpty()) {
                throw new RuntimeException("Template not found: " + templateId);
            }
            
            NotificationTemplate existing = existingOpt.get();
            updateExistingTemplate(existing, updates);
            existing.setUpdatedAt(LocalDateTime.now());
            
            NotificationTemplate saved = templateRepository.save(existing);
            log.info("Updated notification template: {}", templateId);
            return saved;
            
        } catch (Exception e) {
            log.error("Error updating notification template {}: {}", templateId, e.getMessage(), e);
            throw new RuntimeException("Failed to update notification template", e);
        }
    }
    
    public NotificationTemplate getTemplate(String templateId) {
        try {
            return templateRepository.findById(templateId)
                .orElseThrow(() -> new RuntimeException("Template not found: " + templateId));
        } catch (Exception e) {
            log.error("Error getting notification template {}: {}", templateId, e.getMessage(), e);
            throw new RuntimeException("Failed to get notification template", e);
        }
    }
    
    public List<NotificationTemplate> getTemplatesByChannel(NotificationChannel channel) {
        try {
            return templateRepository.findByChannelAndIsActive(channel, true);
        } catch (Exception e) {
            log.error("Error getting templates for channel {}: {}", channel, e.getMessage(), e);
            throw new RuntimeException("Failed to get templates by channel", e);
        }
    }
    
    public List<NotificationTemplate> getTemplatesByType(String notificationType) {
        try {
            return templateRepository.findByNotificationTypeAndIsActive(notificationType, true);
        } catch (Exception e) {
            log.error("Error getting templates for type {}: {}", notificationType, e.getMessage(), e);
            throw new RuntimeException("Failed to get templates by type", e);
        }
    }
    
    public Optional<NotificationTemplate> findTemplateByChannelAndType(NotificationChannel channel, String notificationType) {
        try {
            return templateRepository.findByChannelAndTypeAndActive(channel, notificationType);
        } catch (Exception e) {
            log.error("Error finding template for channel {} and type {}: {}", channel, notificationType, e.getMessage(), e);
            return Optional.empty();
        }
    }
    
    public List<NotificationTemplate> getAllActiveTemplates() {
        try {
            return templateRepository.findByIsActiveOrderByCreatedAtDesc(true);
        } catch (Exception e) {
            log.error("Error getting all active templates: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to get active templates", e);
        }
    }
    
    @Transactional
    public void deactivateTemplate(String templateId) {
        try {
            Optional<NotificationTemplate> templateOpt = templateRepository.findById(templateId);
            if (templateOpt.isPresent()) {
                NotificationTemplate template = templateOpt.get();
                template.setIsActive(false);
                template.setUpdatedAt(LocalDateTime.now());
                templateRepository.save(template);
                log.info("Deactivated notification template: {}", templateId);
            }
        } catch (Exception e) {
            log.error("Error deactivating notification template {}: {}", templateId, e.getMessage(), e);
            throw new RuntimeException("Failed to deactivate notification template", e);
        }
    }
    
    @Transactional
    public void deleteTemplate(String templateId) {
        try {
            templateRepository.deleteById(templateId);
            log.info("Deleted notification template: {}", templateId);
        } catch (Exception e) {
            log.error("Error deleting notification template {}: {}", templateId, e.getMessage(), e);
            throw new RuntimeException("Failed to delete notification template", e);
        }
    }
    
    public String renderTemplate(String template, Map<String, Object> variables) {
        try {
            if (template == null || variables == null) {
                return template;
            }
            
            String result = template;
            Matcher matcher = VARIABLE_PATTERN.matcher(template);
            
            while (matcher.find()) {
                String variableName = matcher.group(1);
                Object value = variables.get(variableName);
                String replacement = value != null ? value.toString() : "";
                result = result.replace("{{" + variableName + "}}", replacement);
            }
            
            return result;
        } catch (Exception e) {
            log.error("Error rendering template: {}", e.getMessage(), e);
            return template;
        }
    }
    
    public RenderedTemplate renderNotificationTemplate(String templateId, Map<String, Object> variables) {
        try {
            NotificationTemplate template = getTemplate(templateId);
            
            String renderedTitle = renderTemplate(template.getTitleTemplate(), variables);
            String renderedMessage = renderTemplate(template.getMessageTemplate(), variables);
            String renderedHtml = template.getHtmlTemplate() != null ? 
                renderTemplate(template.getHtmlTemplate(), variables) : null;
            
            return RenderedTemplate.builder()
                .templateId(templateId)
                .title(renderedTitle)
                .message(renderedMessage)
                .htmlContent(renderedHtml)
                .channel(template.getChannel())
                .notificationType(template.getNotificationType())
                .build();
                
        } catch (Exception e) {
            log.error("Error rendering notification template {}: {}", templateId, e.getMessage(), e);
            throw new RuntimeException("Failed to render notification template", e);
        }
    }
    
    public void validateTemplate(NotificationTemplate template) {
        if (template.getTitleTemplate() == null || template.getTitleTemplate().trim().isEmpty()) {
            throw new IllegalArgumentException("Title template cannot be empty");
        }
        
        if (template.getMessageTemplate() == null || template.getMessageTemplate().trim().isEmpty()) {
            throw new IllegalArgumentException("Message template cannot be empty");
        }
        
        if (template.getChannel() == null) {
            throw new IllegalArgumentException("Channel cannot be null");
        }
        
        // Validate template syntax
        validateTemplateSyntax(template.getTitleTemplate());
        validateTemplateSyntax(template.getMessageTemplate());
        
        if (template.getHtmlTemplate() != null) {
            validateTemplateSyntax(template.getHtmlTemplate());
        }
    }
    
    private void validateTemplateSyntax(String template) {
        Matcher matcher = VARIABLE_PATTERN.matcher(template);
        while (matcher.find()) {
            String variableName = matcher.group(1);
            if (variableName == null || variableName.trim().isEmpty()) {
                throw new IllegalArgumentException("Invalid variable syntax in template");
            }
        }
    }
    
    private void updateExistingTemplate(NotificationTemplate existing, NotificationTemplate updates) {
        if (updates.getName() != null) {
            existing.setName(updates.getName());
        }
        if (updates.getDescription() != null) {
            existing.setDescription(updates.getDescription());
        }
        if (updates.getTitleTemplate() != null) {
            existing.setTitleTemplate(updates.getTitleTemplate());
        }
        if (updates.getMessageTemplate() != null) {
            existing.setMessageTemplate(updates.getMessageTemplate());
        }
        if (updates.getHtmlTemplate() != null) {
            existing.setHtmlTemplate(updates.getHtmlTemplate());
        }
        if (updates.getChannel() != null) {
            existing.setChannel(updates.getChannel());
        }
        if (updates.getNotificationType() != null) {
            existing.setNotificationType(updates.getNotificationType());
        }
        if (updates.getIsActive() != null) {
            existing.setIsActive(updates.getIsActive());
        }
        if (updates.getVariables() != null) {
            existing.setVariables(updates.getVariables());
        }
    }
    
    // Inner class for rendered template result
    public static class RenderedTemplate {
        private String templateId;
        private String title;
        private String message;
        private String htmlContent;
        private NotificationChannel channel;
        private String notificationType;
        
        // Builder pattern
        public static RenderedTemplateBuilder builder() {
            return new RenderedTemplateBuilder();
        }
        
        public static class RenderedTemplateBuilder {
            private String templateId;
            private String title;
            private String message;
            private String htmlContent;
            private NotificationChannel channel;
            private String notificationType;
            
            public RenderedTemplateBuilder templateId(String templateId) {
                this.templateId = templateId;
                return this;
            }
            
            public RenderedTemplateBuilder title(String title) {
                this.title = title;
                return this;
            }
            
            public RenderedTemplateBuilder message(String message) {
                this.message = message;
                return this;
            }
            
            public RenderedTemplateBuilder htmlContent(String htmlContent) {
                this.htmlContent = htmlContent;
                return this;
            }
            
            public RenderedTemplateBuilder channel(NotificationChannel channel) {
                this.channel = channel;
                return this;
            }
            
            public RenderedTemplateBuilder notificationType(String notificationType) {
                this.notificationType = notificationType;
                return this;
            }
            
            public RenderedTemplate build() {
                RenderedTemplate template = new RenderedTemplate();
                template.templateId = this.templateId;
                template.title = this.title;
                template.message = this.message;
                template.htmlContent = this.htmlContent;
                template.channel = this.channel;
                template.notificationType = this.notificationType;
                return template;
            }
        }
        
        // Getters
        public String getTemplateId() { return templateId; }
        public String getTitle() { return title; }
        public String getMessage() { return message; }
        public String getHtmlContent() { return htmlContent; }
        public NotificationChannel getChannel() { return channel; }
        public String getNotificationType() { return notificationType; }
    }
}