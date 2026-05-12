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
@Document(collection = "ar_menus")
public class ARMenuDocument {
    
    @Id
    private String id;
    
    @Indexed(unique = true)
    private Long restaurantId;
    
    private String restaurantName;
    
    // 3D Model information
    private String modelUrl;
    private String modelFormat;  // e.g., "GLB", "GLTF", "FBX"
    private Long modelSize;  // File size in bytes
    
    // Preview and thumbnails
    private String previewImageUrl;
    private List<String> thumbnailUrls;
    
    // AR-specific metadata
    private ARMetadata arMetadata;
    
    // Description and features
    private String description;
    private List<String> features;  // e.g., ["360° View", "Scale Adjustment", "Placement Test"]
    
    // Status and visibility
    private boolean isActive;
    private String status;  // DRAFT, PROCESSING, ACTIVE, INACTIVE
    
    // Version control
    private String version;
    private LocalDateTime uploadedAt;
    private LocalDateTime updatedAt;
    private String uploadedBy;
    
    // Analytics
    private int viewCount;
    private int interactionCount;
    private Map<String, Integer> deviceUsage;  // e.g., {"iOS": 45, "Android": 32}
    
    // Menu items linked to AR models
    private List<ARMenuItem> arMenuItems;
    
    // Configuration
    private ARConfiguration configuration;
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ARMetadata {
        private boolean supportsAndroid;
        private boolean supportsIOS;
        private String minAndroidVersion;
        private String minIOSVersion;
        private List<String> requiredFeatures;  // e.g., ["ARCore", "ARKit"]
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ARMenuItem {
        private Long menuItemId;
        private String menuItemName;
        private String modelUrl;
        private String thumbnailUrl;
        private Map<String, Object> customProperties;  // Flexible properties
    }
    
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ARConfiguration {
        private boolean autoRotate;
        private boolean allowScaling;
        private boolean showShadows;
        private String defaultEnvironment;  // e.g., "indoor", "outdoor", "studio"
        private Map<String, Object> advancedSettings;
    }
}
