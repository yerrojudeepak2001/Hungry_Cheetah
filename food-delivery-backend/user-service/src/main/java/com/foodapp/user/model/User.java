package com.foodapp.user.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = true)
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime dateOfBirth;
    private String gender;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @JsonManagedReference
    private List<Address> addresses;
    
    @ElementCollection
    private Set<String> roles;
    
    @Column(name = "enabled")
    private Boolean isEnabled;
    @Column(name = "email_verified") 
    private Boolean isEmailVerified;
    @Column(name = "phone_verified")
    private Boolean isPhoneVerified;
    @Column(name = "account_non_expired")
    private Boolean accountNonExpired = true;
    @Column(name = "account_non_locked")
    private Boolean accountNonLocked = true;
    @Column(name = "credentials_non_expired")
    private Boolean credentialsNonExpired = true;
    private LocalDateTime lastLogin;
    private LocalDateTime registrationDate;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JsonManagedReference
    private UserPreference preferences;
    
    @ElementCollection
    private Set<Long> favoriteRestaurants;
    
    private String profilePicture;
    private String language;
    private String timezone;
    private String deviceToken;
}