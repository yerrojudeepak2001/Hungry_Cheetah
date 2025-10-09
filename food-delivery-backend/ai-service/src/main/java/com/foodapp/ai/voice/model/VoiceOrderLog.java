package com.foodapp.ai.voice.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Data
@Document(collection = "voice_order_logs")
public class VoiceOrderLog {
    @Id
    private String id;
    private Long userId;
    private String audioTranscript;
    private OrderIntent extractedIntent;
    private Long orderId;
    private LocalDateTime createdAt = LocalDateTime.now();
    private String status;
    private String errorDetails;
}