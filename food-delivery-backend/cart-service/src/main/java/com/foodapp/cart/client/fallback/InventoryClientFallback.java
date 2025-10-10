package com.foodapp.cart.client.fallback;

import com.foodapp.cart.client.InventoryClient;
import com.foodapp.cart.dto.InventoryCheck;
import com.foodapp.cart.dto.StockStatus;
import com.foodapp.cart.dto.ReservationRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Collections;

@Component
public class InventoryClientFallback implements InventoryClient {

    @Override
    public List<StockStatus> checkItemsAvailability(InventoryCheck check) {
        return Collections.emptyList();
    }

    @Override
    public StockStatus getItemStock(String itemId) {
        StockStatus status = new StockStatus();
        status.setItemId(itemId);
        status.setIsAvailable(false);
        status.setStatus("UNAVAILABLE");
        return status;
    }

    @Override
    public boolean reserveItems(ReservationRequest request) {
        return false;
    }
}