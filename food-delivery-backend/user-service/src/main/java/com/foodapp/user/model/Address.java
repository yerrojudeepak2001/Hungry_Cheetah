package com.foodapp.user.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "addresses")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String street;
    private String city;
    private String state;
    private String country;
    private String zipCode;
    private String addressType; // HOME, WORK, OTHER
    
    @Column(name = "is_default")
    private boolean isDefault = false;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}