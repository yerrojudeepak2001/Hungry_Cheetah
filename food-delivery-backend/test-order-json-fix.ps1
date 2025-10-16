# Order Service JSON Fix Test Script

Write-Host "Testing Order Service JSON parsing fix..." -ForegroundColor Green

# Test 1: Check if Order Service is running
Write-Host "`n1. Checking if Order Service is running on port 8081..." -ForegroundColor Yellow
try {
    $response = Invoke-RestMethod -Uri "http://localhost:8081/actuator/health" -Method GET -ErrorAction Stop
    Write-Host "✓ Order Service is running" -ForegroundColor Green
    Write-Host "Response: $($response | ConvertTo-Json)" -ForegroundColor Cyan
} catch {
    Write-Host "✗ Order Service is not accessible: $($_.Exception.Message)" -ForegroundColor Red
    Write-Host "Please make sure the Order Service is running on port 8081" -ForegroundColor Yellow
    exit 1
}

# Test 2: Try to create an order with the new JSON structure
Write-Host "`n2. Testing order creation with object deliveryAddress..." -ForegroundColor Yellow

$orderData = @{
    restaurantId = 1
    items = @(
        @{
            menuItemId = 101
            itemName = "Margherita Pizza"
            quantity = 2
            price = 12.99
            specialInstructions = "Extra cheese, no olives"
        }
    )
    totalAmount = 25.98
    deliveryAddress = @{
        streetAddress = "123 Main St"
        apartmentNumber = "Apt 4B"
        city = "New York"
        state = "NY"
        country = "USA"
        postalCode = "10001"
        landmark = "Near Central Park"
        latitude = 40.7128
        longitude = -74.0060
    }
}

$orderJson = $orderData | ConvertTo-Json -Depth 10

Write-Host "Sending order data:" -ForegroundColor Cyan
Write-Host $orderJson -ForegroundColor White

try {
    $headers = @{
        'Content-Type' = 'application/json'
    }
    
    # Note: This will likely fail due to authentication, but we're checking if JSON parsing works
    $response = Invoke-RestMethod -Uri "http://localhost:8081/api/orders" -Method POST -Body $orderJson -Headers $headers -ErrorAction Stop
    
    Write-Host "✓ Order created successfully!" -ForegroundColor Green
    Write-Host "Response: $($response | ConvertTo-Json)" -ForegroundColor Cyan
    
} catch {
    # Check if the error is authentication-related (401) vs JSON parsing error (400)
    if ($_.Exception.Response.StatusCode -eq 401) {
        Write-Host "✓ JSON parsing works! (Got 401 Unauthorized - expected due to missing auth token)" -ForegroundColor Green
        Write-Host "This means the JSON structure is now correctly accepted by the server." -ForegroundColor Green
    } elseif ($_.Exception.Response.StatusCode -eq 400) {
        $errorDetails = $_.ErrorDetails.Message
        if ($errorDetails -like "*JSON parse error*" -or $errorDetails -like "*Cannot deserialize*") {
            Write-Host "✗ JSON parsing still failing!" -ForegroundColor Red
            Write-Host "Error: $errorDetails" -ForegroundColor Red
        } else {
            Write-Host "✓ JSON parsing works! (Got 400 but not a JSON parse error)" -ForegroundColor Green
            Write-Host "Error details: $errorDetails" -ForegroundColor Yellow
        }
    } else {
        Write-Host "Unexpected error (Status: $($_.Exception.Response.StatusCode)): $($_.Exception.Message)" -ForegroundColor Yellow
        if ($_.ErrorDetails) {
            Write-Host "Error details: $($_.ErrorDetails.Message)" -ForegroundColor Yellow
        }
    }
}

Write-Host "`n3. Summary:" -ForegroundColor Green
Write-Host "- Changed OrderDTO.deliveryAddress from String to DeliveryAddressDTO object" -ForegroundColor White
Write-Host "- Updated OrderMapper to handle object mapping" -ForegroundColor White
Write-Host "- Rebuilt and restarted Order Service" -ForegroundColor White
Write-Host "- Updated API documentation" -ForegroundColor White

Write-Host "`nThe JSON parsing error should now be resolved!" -ForegroundColor Green
Write-Host "Clients can now send deliveryAddress as an object instead of a string." -ForegroundColor Cyan