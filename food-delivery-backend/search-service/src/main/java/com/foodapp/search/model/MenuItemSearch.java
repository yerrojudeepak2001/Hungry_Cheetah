package com.foodapp.search.model;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuItemSearch {
    @Field(type = FieldType.Text)
    private String name;
    
    @Field(type = FieldType.Text)
    private String description;
    
    @Field(type = FieldType.Double)
    private Double price;
    
    @Field(type = FieldType.Keyword)
    private String category;
    
    @Field(type = FieldType.Boolean)
    private Boolean isVegetarian;
    
    @Field(type = FieldType.Double)
    private Double rating;
    
    @Field(type = FieldType.Integer)
    private Integer orderCount;
    
    @Field(type = FieldType.Text)
    private String imageUrl;
}