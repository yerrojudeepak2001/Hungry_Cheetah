package com.foodapp.pricing.client.fallback;

import com.foodapp.pricing.client.OrderClient;
import com.foodapp.pricing.dto.OrderPricingInfo;
import com.foodapp.pricing.dto.PricingUpdate;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.HashMap;
import java.math.BigDecimal;

@Component
public class OrderClientFallback implements OrderClient {

    @Override
    public OrderPricingInfo getOrderPricingInfo(String orderId) {
        // Return default pricing info when order service is unavailable
        OrderPricingInfo defaultInfo = new OrderPricingInfo();
        defaultInfo.setOrderId(Long.parseLong(orderId));
        defaultInfo.setSubTotal(BigDecimal.ZERO);
        defaultInfo.setDeliveryFee(BigDecimal.ZERO);
        defaultInfo.setServiceFee(BigDecimal.ZERO);
        defaultInfo.setTaxes(BigDecimal.ZERO);
        defaultInfo.setDiscount(BigDecimal.ZERO);
        defaultInfo.setTotal(BigDecimal.ZERO);
        defaultInfo.setStatus("SERVICE_UNAVAILABLE");
        return defaultInfo;
    }

    @Override
    public void updateOrderPricing(String orderId, PricingUpdate update) {
        // Do nothing when order service is unavailable
        // In a real implementation, you might queue this for later processing
    }

    @Override
    public Map<String, Object> getDemandMetrics(String timeframe) {
        // Return empty metrics when order service is unavailable
        Map<String, Object> defaultMetrics = new HashMap<>();
        defaultMetrics.put("demandLevel", "UNKNOWN");
        defaultMetrics.put("orderCount", 0);
        defaultMetrics.put("serviceAvailable", false);
        return defaultMetrics;
    }
    
    @Override
    public int getActiveOrdersCount(String area) {
        return 0; // Default fallback value when service is unavailable
    }
}