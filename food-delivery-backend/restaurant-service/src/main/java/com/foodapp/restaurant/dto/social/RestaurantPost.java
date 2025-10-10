package com.foodapp.restaurant.dto.social;

import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestaurantPost {
    private Long id;
    private Long restaurantId;
    private String content;
    private List<String> mediaUrls;
    private String postType;
    private LocalDateTime postDate;
    private Integer likes;
    private Integer shares;
    private Integer comments;
    private String status;
}