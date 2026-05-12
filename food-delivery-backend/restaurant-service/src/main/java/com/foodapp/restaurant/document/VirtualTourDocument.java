package com.foodapp.restaurant.document;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "virtual_tours")
public class VirtualTourDocument {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private Long restaurantId;
    
    private String restaurantName;
    
    // Tour media
    private String tourUrl;
    private String tourType;  // "360_PANORAMA", "3D_WALKTHROUGH", "VIDEO_TOUR"
    private String thumbnailUrl;
    private List<String> previewImages;
    
    // Tour structure
    private List<TourStop> tourStops;
    
    // Metadata
    private String description;
    private List<String> highlights;  // Key features to showcase
    private String duration;  // e.g., "3 minutes"
    
    // Media specifications
    private MediaSpecs mediaSpecs;
    
    // Status and visibility
    private boolean isActive;
    private String status;  // DRAFT, PROCESSING, ACTIVE, INACTIVE
    
    // Timestamps
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    
    // Analytics
    private int viewCount;
    private int completionCount;  // Users who completed the full tour
    private double averageViewDuration;  // in seconds
    private Map<String, Integer> deviceBreakdown;
    
    // Interactive elements
    private List<HotSpot> hotSpots;
    
    // Audio guide
    private AudioGuide audioGuide;
    
    // Accessibility
    private AccessibilityFeatures accessibilityFeatures;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class TourStop {
        private String id;
        private String name;
        private String description;
        private String imageUrl;
        private String panoramaUrl;
        private int order;
        private double latitude;
        private double longitude;
        private List<String> connectedStops;  // IDs of connected tour stops
        private Map<String, Object> metadata;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class MediaSpecs {
        private String resolution;  // e.g., "4K", "8K"
        private String format;  // e.g., "MP4", "WEBM"
        private Long fileSize;
        private int fps;
        private String codec;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HotSpot {
        private String id;
        private String type;  // "INFO", "MENU", "IMAGE", "VIDEO", "LINK"
        private String title;
        private String content;
        private String targetUrl;
        private Map<String, Double> position;  // x, y coordinates or lat, lng
        private String iconUrl;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AudioGuide {
        private boolean enabled;
        private String audioUrl;
        private List<AudioSegment> segments;
        private List<String> availableLanguages;
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AudioSegment {
        private String tourStopId;
        private String audioUrl;
        private String transcript;
        private int duration;  // in seconds
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class AccessibilityFeatures {
        private boolean hasSubtitles;
        private boolean hasAudioDescription;
        private boolean hasHighContrastMode;
        private List<String> supportedLanguages;
    }
}
