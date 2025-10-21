package com.foodapp.ai.gemini.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class GeminiRequest {
    private Contents contents;
    @JsonProperty("generationConfig")
    private GenerationConfig generationConfig;

    public GeminiRequest() {}

    public GeminiRequest(String text) {
        this.contents = new Contents();
        this.contents.setParts(List.of(new Part(text)));
        
        this.generationConfig = new GenerationConfig();
        this.generationConfig.setTemperature(0.7);
        this.generationConfig.setMaxOutputTokens(1000);
    }

    public static class Contents {
        private List<Part> parts;

        public List<Part> getParts() {
            return parts;
        }

        public void setParts(List<Part> parts) {
            this.parts = parts;
        }
    }

    public static class Part {
        private String text;

        public Part() {}

        public Part(String text) {
            this.text = text;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }
    }

    public static class GenerationConfig {
        private Double temperature;
        @JsonProperty("maxOutputTokens")
        private Integer maxOutputTokens;

        public Double getTemperature() {
            return temperature;
        }

        public void setTemperature(Double temperature) {
            this.temperature = temperature;
        }

        public Integer getMaxOutputTokens() {
            return maxOutputTokens;
        }

        public void setMaxOutputTokens(Integer maxOutputTokens) {
            this.maxOutputTokens = maxOutputTokens;
        }
    }

    // Getters and Setters
    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }

    public GenerationConfig getGenerationConfig() {
        return generationConfig;
    }

    public void setGenerationConfig(GenerationConfig generationConfig) {
        this.generationConfig = generationConfig;
    }
}