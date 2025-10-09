package com.foodapp.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.http.MediaType;
import org.springframework.http.HttpHeaders;
import java.util.Collections;
import java.util.List;

public class RequestUtils {
    private static final ObjectMapper objectMapper = new ObjectMapper()
        .registerModule(new JavaTimeModule());
        
    public static HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
    
    public static <T> String toJson(T object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException("Error converting object to JSON", e);
        }
    }
    
    public static <T> T fromJson(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON to object", e);
        }
    }
    
    public static <T> List<T> fromJsonArray(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json,
                objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            throw new RuntimeException("Error converting JSON array to list", e);
        }
    }
    
    public static String getUserAgent(String serviceName, String version) {
        return String.format("FoodApp-%s/%s", serviceName, version);
    }
    
    public static String buildUrl(String baseUrl, String... pathSegments) {
        StringBuilder url = new StringBuilder(baseUrl);
        for (String segment : pathSegments) {
            if (!url.toString().endsWith("/") && !segment.startsWith("/")) {
                url.append("/");
            }
            url.append(segment);
        }
        return url.toString();
    }
}