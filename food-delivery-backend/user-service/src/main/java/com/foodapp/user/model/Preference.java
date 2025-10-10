package com.foodapp.user.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "preferences")
public class Preference {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String name;
    private String value;
    private String category; // DIETARY, NOTIFICATION, PRIVACY, etc.
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}