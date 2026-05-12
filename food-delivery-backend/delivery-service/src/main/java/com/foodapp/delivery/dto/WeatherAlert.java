package com.foodapp.delivery.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WeatherAlert {
    private String type;
    private String severity;
    private String description;
    private String startTime;
    private String endTime;
    private List<String> affectedAreas;
    private String instructions;
}