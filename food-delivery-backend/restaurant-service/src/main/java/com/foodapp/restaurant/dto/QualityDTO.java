package com.foodapp.restaurant.dto;

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
public class QualityAudit {
    private Long restaurantId;
    private LocalDateTime auditDate;
    private List<AuditFinding> findings;
    private String overallRating;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditFinding {
    private String category;
    private String observation;
    private String severity;
    private boolean requiresAction;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QualityViolation {
    private Long restaurantId;
    private String violationType;
    private String description;
    private LocalDateTime reportedAt;
    private String status;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImprovementPlan {
    private Long restaurantId;
    private List<ImprovementAction> actions;
    private LocalDateTime createdAt;
    private LocalDateTime deadline;
}

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImprovementAction {
    private String description;
    private String priority;
    private String status;
    private LocalDateTime completionDate;
}