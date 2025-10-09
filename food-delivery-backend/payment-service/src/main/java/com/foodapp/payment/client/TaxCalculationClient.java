package com.foodapp.payment.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import com.foodapp.payment.dto.TaxCalculationRequest;

@FeignClient(name = "TAX-SERVICE", fallback = TaxCalculationClientFallback.class)
public interface TaxCalculationClient {
    @PostMapping("/api/tax/calculate")
    TaxCalculationResponse calculateTax(@RequestBody TaxCalculationRequest request);
    
    @GetMapping("/api/tax/rates/{locationId}")
    TaxRates getTaxRates(@PathVariable("locationId") String locationId);
    
    @GetMapping("/api/tax/exemptions/{userId}")
    List<TaxExemption> getUserTaxExemptions(@PathVariable("userId") String userId);
    
    @PostMapping("/api/tax/invoice")
    TaxInvoice generateTaxInvoice(@RequestBody InvoiceGenerationRequest request);
}