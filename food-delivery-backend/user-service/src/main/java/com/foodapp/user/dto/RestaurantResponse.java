package com.foodapp.user.dto;

<<<<<<< HEAD
public class RestaurantResponse {
    private Long id;
=======
import lombok.Data;

@Data
public class RestaurantResponse {
    private String restaurantId;
>>>>>>> version1.4
    private String name;
    private String cuisine;
    private Double rating;
    private String address;
<<<<<<< HEAD
    private Boolean isOpen;

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(Boolean isOpen) {
        this.isOpen = isOpen;
    }
=======
>>>>>>> version1.4
}