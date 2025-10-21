package com.foodapp.cart.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "cart_sharing")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartSharing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cart_id", nullable = false)
    private Long cartId;

    @Column(name = "owner_user_id", nullable = false)
    private String ownerUserId;

    @Column(name = "shared_with_user_id")
    private String sharedWithUserId;

    @Column(name = "share_token", unique = true)
    private String shareToken; // For sharing via link

    @Column(name = "share_type", nullable = false)
    private String shareType; // DIRECT_USER, PUBLIC_LINK, FAMILY_GROUP

    @Column(name = "permission_level")
    @Builder.Default
    private String permissionLevel = "VIEW"; // VIEW, EDIT, FULL_ACCESS

    @Column(name = "is_active")
    @Builder.Default
    private Boolean isActive = true;

    @Column(name = "expires_at")
    private LocalDateTime expiresAt;

    @Column(name = "max_uses")
    private Integer maxUses;

    @Column(name = "use_count")
    @Builder.Default
    private Integer useCount = 0;

    @Column(name = "shared_at")
    private LocalDateTime sharedAt;

    @Column(name = "last_accessed_at")
    private LocalDateTime lastAccessedAt;

    @Column(name = "last_accessed_by")
    private String lastAccessedBy;

    @Column(name = "share_message")
    private String shareMessage; // Custom message when sharing

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        sharedAt = LocalDateTime.now();
        if (expiresAt == null) {
            expiresAt = LocalDateTime.now().plusHours(72); // Default 3 days
        }
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Helper method to check if sharing is still valid
    public boolean isValid() {
        return isActive && 
               (expiresAt == null || LocalDateTime.now().isBefore(expiresAt)) &&
               (maxUses == null || useCount < maxUses);
    }
}