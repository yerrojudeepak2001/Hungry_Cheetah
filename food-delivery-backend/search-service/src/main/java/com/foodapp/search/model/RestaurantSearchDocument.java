package com.foodapp.search.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.annotation.Id;
import java.util.List;
import java.util.Map;

@Data
@Document(indexName = "restaurants")
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantSearchDocument {
    @Id
    private String id;
    
    @Field(type = FieldType.Text)
    private String name;
    
    @Field(type = FieldType.Text)
    private String description;
    
    @Field(type = FieldType.Keyword)
    private List<String> cuisines;
    
    @Field(type = FieldType.Keyword)
    private List<String> categories;
    
    @Field(type = FieldType.Text)
    private String address;
    
    @Field(type = FieldType.Double)
    private Double latitude;
    
    @Field(type = FieldType.Double)
    private Double longitude;
    
    @Field(type = FieldType.Double)
    private Double rating;
    
    @Field(type = FieldType.Integer)
    private Integer reviewCount;
    
    @Field(type = FieldType.Boolean)
    private Boolean isOpen;
    
    @Field(type = FieldType.Nested)
    private List<MenuItemSearch> popularItems;
    
    @Field(type = FieldType.Keyword)
    private List<String> features;
    
    @Field(type = FieldType.Double)
    private Double priceRange;
    
    @Field(type = FieldType.Integer)
    private Integer preparationTime;
    
    @Field(type = FieldType.Object)
    private Map<String, String> operatingHours;
    
    @Field(type = FieldType.Keyword)
    private List<String> tags;
}