package com.foodapp.notification.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.notification.model.NotificationTemplate;
import com.foodapp.notification.dto.TemplateRenderRequest;
import com.foodapp.notification.dto.ValidationResult;
import java.util.List;
import java.util.Map;

@FeignClient(name = "TEMPLATE-SERVICE", fallback = TemplateClientFallback.class)
public interface TemplateClient {
    @GetMapping("/api/templates/{templateId}")
    NotificationTemplate getTemplate(@PathVariable("templateId") String templateId,
                                   @RequestParam String locale);
    
    @GetMapping("/api/templates/type/{type}")
    List<NotificationTemplate> getTemplatesByType(@PathVariable("type") String type);
    
    @PostMapping("/api/templates/render")
    String renderTemplate(@RequestBody TemplateRenderRequest request);
    
    @GetMapping("/api/templates/validate")
    ValidationResult validateTemplate(@RequestParam String templateId,
                                    @RequestBody Map<String, Object> parameters);
}