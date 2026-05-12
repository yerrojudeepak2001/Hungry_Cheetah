package com.foodapp.restaurant.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import java.util.List;

@Data
@Entity
@Table(name = "restaurants")
public class Restaurant {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String address;
    private String cuisineType;
    private Double rating;
    private String contactNumber;
    private String email;
    private Boolean isActive;

    // 🆕 New Field: Image URL
    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<MenuItem> menu;

    @Column(name = "opening_hours")
    private String openingHours;

    @Column(name = "delivery_radius")
    private Double deliveryRadius;

    @Column(name = "minimum_order")
    private Double minimumOrder;

    @ElementCollection
    private List<String> tags;
}
