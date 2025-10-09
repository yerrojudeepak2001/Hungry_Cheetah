package com.foodapp.admin.model;

import lombok.Data;
import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "admin_users")
public class AdminUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String username;
    
    @Column(nullable = false)
    private String password;
    
    @Column(nullable = false)
    private String role; // SUPER_ADMIN, ADMIN, MODERATOR
    
    private String firstName;
    private String lastName;
    private String email;
    
    @Column(nullable = false)
    private Boolean isActive;
    
    private LocalDateTime lastLoginDate;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    private LocalDateTime updatedAt;
    
    private String permissions; // JSON string of permissions
    
    private String department;
    
    @Column(nullable = false)
    private Boolean isMfaEnabled;
    
    private String mfaSecret;
}