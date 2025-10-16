package com.foodapp.search.dto;

import java.util.List;

public class ImageSearchRequest {
    private String imageData;
    private String imageUrl;
    private String imageFormat;
    private List<String> detectedObjects;
    private List<String> detectedText;
    private Double confidence;

    // Default constructor
    public ImageSearchRequest() {}

    // Getters and setters
    public String getImageData() { return imageData; }
    public void setImageData(String imageData) { this.imageData = imageData; }

    public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }

    public String getImageFormat() { return imageFormat; }
    public void setImageFormat(String imageFormat) { this.imageFormat = imageFormat; }

    public List<String> getDetectedObjects() { return detectedObjects; }
    public void setDetectedObjects(List<String> detectedObjects) { this.detectedObjects = detectedObjects; }

    public List<String> getDetectedText() { return detectedText; }
    public void setDetectedText(List<String> detectedText) { this.detectedText = detectedText; }

    public Double getConfidence() { return confidence; }
    public void setConfidence(Double confidence) { this.confidence = confidence; }
}