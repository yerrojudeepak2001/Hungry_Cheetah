package com.foodapp.restaurant.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Data
@Document(collection = "catering_services")
public class CateringService {
    @Id
    private String id;
    private Long restaurantId;
    private Boolean cateringEnabled;
    
    // Menu Options
    private List<CateringMenu> cateringMenus;
    private List<PartyPackage> partyPackages;
    private List<BuffetOption> buffetOptions;
    
    // Service Details
    private Integer minAdvanceHours;
    private Integer maxAdvanceHours;
    private Integer minGuests;
    private Integer maxGuests;
    private BigDecimal minOrderValue;
    
    // Additional Services
    private Boolean setupServiceAvailable;
    private Boolean serverStaffAvailable;
    private Boolean equipmentRentalAvailable;
    private Boolean cleanupServiceAvailable;
    
    @Data
    public static class CateringMenu {
        private String menuId;
        private String name;
        private String description;
        private String cuisine;
        private BigDecimal pricePerPerson;
        private Integer minPeople;
        private List<CateringMenuItem> items;
        private List<String> dietaryOptions;
        private Boolean customizable;
    }
    
    @Data
    public static class PartyPackage {
        private String packageId;
        private String name;
        private String description;
        private Integer servingSize;
        private BigDecimal price;
        private List<CateringMenuItem> items;
        private List<String> includedServices;
        private Map<String, BigDecimal> addOns;
    }
    
    @Data
    public static class BuffetOption {
        private String optionId;
        private String name;
        private String type; // BREAKFAST, LUNCH, DINNER
        private BigDecimal pricePerPerson;
        private List<String> courses;
        private List<CateringMenuItem> items;
        private Boolean vegetarianAvailable;
        private Boolean veganAvailable;
    }
    
    @Data
    public static class CateringMenuItem {
        private String itemId;
        private String name;
        private String description;
        private String category;
        private Integer servingSize;
        private List<String> allergens;
        private List<String> dietaryLabels;
        private Boolean requiresHeating;
        private String servingInstructions;
    }
}