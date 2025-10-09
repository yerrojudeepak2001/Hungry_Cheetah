package com.foodapp.user.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import javax.persistence.*;
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
    
    private String username;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private String phone;
    private LocalDateTime dateOfBirth;
    private String gender;
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<Address> addresses;
    
    @ElementCollection
    private Set<String> roles;
    
    private Boolean isEnabled;
    private Boolean isEmailVerified;
    private Boolean isPhoneVerified;
    private LocalDateTime lastLogin;
    private LocalDateTime registrationDate;
    
    @OneToOne(cascade = CascadeType.ALL)
    private UserPreference preferences;
    
    @ElementCollection
    private Set<Long> favoriteRestaurants;
    
    private String profilePicture;
    private String language;
    private String timezone;
    private String deviceToken;
}