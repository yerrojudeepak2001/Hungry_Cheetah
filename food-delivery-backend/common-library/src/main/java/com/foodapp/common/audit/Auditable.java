package com.foodapp.common.audit;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public abstract class Auditable {
    
    private String createdBy;
    private LocalDateTime createdDate;
    private String lastModifiedBy;
    private LocalDateTime lastModifiedDate;
    private String recordStatus = "ACTIVE";
}