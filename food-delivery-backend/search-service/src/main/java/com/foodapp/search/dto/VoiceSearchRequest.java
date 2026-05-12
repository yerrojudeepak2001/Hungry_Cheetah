package com.foodapp.search.dto;

public class VoiceSearchRequest {
    private String audioData;
    private String audioFormat;
    private String language;
    private String transcription;
    private Double confidence;

    // Default constructor
    public VoiceSearchRequest() {}

    // Constructor with transcription
    public VoiceSearchRequest(String transcription) {
        this.transcription = transcription;
    }

    // Getters and setters
    public String getAudioData() { return audioData; }
    public void setAudioData(String audioData) { this.audioData = audioData; }

    public String getAudioFormat() { return audioFormat; }
    public void setAudioFormat(String audioFormat) { this.audioFormat = audioFormat; }

    public String getLanguage() { return language; }
    public void setLanguage(String language) { this.language = language; }

    public String getTranscription() { return transcription; }
    public void setTranscription(String transcription) { this.transcription = transcription; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
}