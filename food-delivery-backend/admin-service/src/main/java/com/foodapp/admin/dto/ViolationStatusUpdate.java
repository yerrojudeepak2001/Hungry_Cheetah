package com.foodapp.admin.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ViolationStatusUpdate {
    private String status;
    private String comments;
    private String updatedBy;
}