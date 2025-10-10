package com.foodapp.restaurant.client;

import com.foodapp.restaurant.dto.*;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.time.LocalDateTime;

@Component
public class InventoryClientFallback implements InventoryClient {
    @Override
    public List<StockLevel> getCurrentStockLevels(Long restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public StockLevel updateInventory(Long restaurantId, InventoryUpdate update) {
        return null;
    }

    @Override
    public List<InventoryAlert> getInventoryAlerts(Long restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public InventoryForecast getInventoryForecast(Long restaurantId, Long itemId) {
        return null;
    }
}

@Component
public class OrderClientFallback implements OrderClient {
    @Override
    public List<OrderResponse> getRestaurantOrders(Long restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public OrderResponse getOrderDetails(Long orderId) {
        return null;
    }
}

@Component
public class DeliveryClientFallback implements DeliveryClient {
    @Override
    public DeliveryTimeResponse getEstimatedDeliveryTime(Long restaurantId, Long orderId) {
        return null;
    }
}

@Component
public class FeedbackClientFallback implements FeedbackClient {
    @Override
    public List<FeedbackData> getRestaurantFeedback(Long restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public List<FeedbackData> getRecentFeedback(Long restaurantId, int limit) {
        return Collections.emptyList();
    }

    @Override
    public FeedbackData saveFeedback(FeedbackData feedback) {
        return null;
    }

    @Override
    public RatingInfo getRestaurantRatingInfo(Long restaurantId) {
        return null;
    }
}

@Component
public class ComplianceClientFallback implements ComplianceClient {
    @Override
    public ComplianceResult performComplianceCheck(ComplianceCheck check) {
        return null;
    }

    @Override
    public List<ComplianceRequirement> getComplianceRequirements() {
        return Collections.emptyList();
    }

    @Override
    public List<RegulationUpdate> getRegulationUpdates() {
        return Collections.emptyList();
    }

    @Override
    public CertificateVerification verifyCertificate(Long restaurantId) {
        return null;
    }
}

@Component
public class KitchenClientFallback implements KitchenClient {
    @Override
    public void submitOrder(KitchenOrder order) {
        // Do nothing in fallback
    }

    @Override
    public KitchenStatus getKitchenStatus(Long restaurantId) {
        return null;
    }

    @Override
    public Map<String, Integer> getPreparationTimes() {
        return Collections.emptyMap();
    }

    @Override
    public void updateKitchenStatus(KitchenStatusUpdate update) {
        // Do nothing in fallback
    }
}

@Component
public class RecommendationClientFallback implements RecommendationClient {
    @Override
    public RecommendationResponse getRecommendations(RecommendationRequest request) {
        return null;
    }

    @Override
    public List<RecommendationResponse> getPopularItems(Long restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public List<RecommendationResponse> getPersonalizedRecommendations(Long userId) {
        return Collections.emptyList();
    }

    @Override
    public void submitRecommendationFeedback(RecommendationFeedback feedback) {
        // Do nothing in fallback
    }
}

@Component
public class MenuClientFallback implements MenuClient {
    @Override
    public MenuData getRestaurantMenu(Long restaurantId) {
        return null;
    }

    @Override
    public void updateMenuItemAvailability(Long restaurantId, MenuItemAvailability availability) {
        // Do nothing in fallback
    }

    @Override
    public List<MenuData> getBulkMenuData(List<Long> restaurantIds) {
        return Collections.emptyList();
    }

    @Override
    public void syncMenuItems(MenuSyncRequest request) {
        // Do nothing in fallback
    }
}

@Component
public class POSIntegrationClientFallback implements POSIntegrationClient {
    @Override
    public void syncMenu(POSMenuSync menuSync) {
        // Do nothing in fallback
    }

    @Override
    public Map<String, String> getPOSSettings(Long restaurantId) {
        return Collections.emptyMap();
    }

    @Override
    public void submitOrder(POSOrderData orderData) {
        // Do nothing in fallback
    }

    @Override
    public POSIntegrationStatus getIntegrationStatus(Long restaurantId) {
        return null;
    }
}

@Component
public class QualityClientFallback implements QualityClient {
    @Override
    public QualityCheck performQualityCheck(Long restaurantId) {
        return null;
    }

    @Override
    public List<SafetyReport> getSafetyReports(Long restaurantId) {
        return Collections.emptyList();
    }

    @Override
    public void reportQualityIncident(QualityIncident incident) {
        // Do nothing in fallback
    }

    @Override
    public List<QualityStandard> getQualityStandards() {
        return Collections.emptyList();
    }
}

@Component
public class QualityServiceClientFallback implements QualityServiceClient {
    @Override
    public void submitQualityAudit(QualityAudit audit) {
        // Do nothing in fallback
    }

    @Override
    public List<QualityStandard> getCurrentStandards() {
        return Collections.emptyList();
    }

    @Override
    public List<QualityViolation> getViolations(Long restaurantId, LocalDateTime start, LocalDateTime end) {
        return Collections.emptyList();
    }

    @Override
    public void submitImprovementPlan(ImprovementPlan plan) {
        // Do nothing in fallback
    }
}