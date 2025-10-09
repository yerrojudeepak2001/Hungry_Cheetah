package com.foodapp.common.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class MessageQueueService {
    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;
    
    public MessageQueueService(JmsTemplate jmsTemplate) {
        this.jmsTemplate = jmsTemplate;
        this.objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }
    
    public <T> void send(String destination, T message) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            jmsTemplate.convertAndSend(destination, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting message to JSON", e);
        }
    }
    
    public <T> T receive(String destination, Class<T> clazz) {
        String jsonMessage = (String) jmsTemplate.receiveAndConvert(destination);
        try {
            return objectMapper.readValue(jsonMessage, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting JSON to message", e);
        }
    }
    
    public <T> void sendWithDelay(String destination, T message, long delayMillis) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            jmsTemplate.setDeliveryDelay(delayMillis);
            jmsTemplate.convertAndSend(destination, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting message to JSON", e);
        } finally {
            jmsTemplate.setDeliveryDelay(0); // Reset delay
        }
    }
    
    public <T> void sendWithPriority(String destination, T message, int priority) {
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            jmsTemplate.setPriority(priority);
            jmsTemplate.convertAndSend(destination, jsonMessage);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error converting message to JSON", e);
        } finally {
            jmsTemplate.setPriority(4); // Reset to default priority
        }
    }
    
    public boolean checkDestination(String destination) {
        return jmsTemplate.browse(destination, (session, browser) -> browser.getEnumeration().hasMoreElements());
    }
    
    public void purgeDestination(String destination) {
        while (checkDestination(destination)) {
            jmsTemplate.receive(destination);
        }
    }
}