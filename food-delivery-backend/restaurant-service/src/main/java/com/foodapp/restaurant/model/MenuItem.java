package com.foodapp.restaurant.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@Table(name = "menu_items")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "restaurant_id")
    @JsonBackReference
    private Restaurant restaurant;

    private String name;
    private String description;
    private BigDecimal price;
    private String category;
    private Boolean isVegetarian;
    private Boolean isSpicy;
    private Boolean isAvailable;
    private String imageUrl;
    
    @ElementCollection
    private List<String> ingredients;
    
    @ElementCollection
    private List<String> allergens;
    
    @Column(name = "preparation_time")
    private Integer preparationTime;
    
    @Column(name = "calories")
    private Integer calories;
}