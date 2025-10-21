package com.foodapp.notification.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TemplateRenderRequest {
    private String templateId;
    private Map<String, Object> variables;
    private String channel;
}