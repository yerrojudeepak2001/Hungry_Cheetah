package com.foodapp.common.event;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DomainEvent<T> {
    private String eventId;
    private String eventType;
    private T payload;
    private String source;
    private LocalDateTime timestamp;
    private String userId;
    private String traceId;
    private Integer version;
    private Map<String, String> metadata;
}