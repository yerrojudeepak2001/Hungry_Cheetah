# Order Service API Test Documentation

## Base Information
- **Service Name**: Order Service
- **Base URL**: `http://localhost:8081`
- **API Prefix**: `/api/orders`
- **Authentication**: JWT Bearer Token required for most endpoints
- **Content-Type**: `application/json`

## Authentication Setup in Postman

1. **Get JWT Token** (from Auth Service):
   ```http
   POST http://localhost:8080/api/auth/login
   Content-Type: application/json
   
   {
     "email": "user@example.com",
     "password": "password123"
   }
   ```

2. **Set Bearer Token**:
   - Copy the token from login response
   - In Postman, go to Authorization tab
   - Select "Bearer Token" type
   - Paste the token in Token field

## API Endpoints

### 1. Regular Order Management

#### 1.1 Create Order
```http
POST http://localhost:8081/api/orders
Content-Type: application/json
Authorization: Bearer <your_jwt_token>

{
  "restaurantId": 1,
  "items": [
    {
      "menuItemId": 101,
      "itemName": "Margherita Pizza",
      "quantity": 2,
      "price": 12.99,
      "specialInstructions": "Extra cheese, no olives"
    },
    {
      "menuItemId": 102,
      "itemName": "Caesar Salad",
      "quantity": 1,
      "price": 8.50,
      "specialInstructions": "Dressing on the side"
    }
  ],
  "totalAmount": 34.48,
  "deliveryAddress": {
    "streetAddress": "123 Main St",
    "apartmentNumber": "Apt 4B",
    "city": "New York",
    "state": "NY",
    "country": "USA",
    "postalCode": "10001",
    "landmark": "Near Central Park",
    "latitude": 40.7128,
    "longitude": -74.0060
  }
}
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Order created successfully",
  "data": {
    "id": 1001,
    "userId": 1,
    "restaurantId": 1,
    "items": [...],
    "totalAmount": 34.48,
    "orderStatus": "PENDING",
    "deliveryAddress": {
      "streetAddress": "123 Main St",
      "apartmentNumber": "Apt 4B",
      "city": "New York",
      "state": "NY",
      "country": "USA",
      "postalCode": "10001",
      "landmark": "Near Central Park",
      "latitude": 40.7128,
      "longitude": -74.0060
    },
    "createdAt": "2025-10-16T12:30:00",
    "updatedAt": "2025-10-16T12:30:00"
  }
}
```

#### 1.2 Get Order Details
```http
GET http://localhost:8081/api/orders/1001
Authorization: Bearer <your_jwt_token>
```

**Expected Response:**
```json
{
  "success": true,
  "message": "Order fetched successfully",
  "data": {
    "id": 1001,
    "userId": 1,
    "restaurantId": 1,
    "orderStatus": "CONFIRMED",
    "totalAmount": 34.48,
    "deliveryAddress": "123 Main St, City, State 12345",
    "estimatedDeliveryTime": "2025-10-16T13:15:00"
  }
}
```

#### 1.3 Get User Orders
```http
GET http://localhost:8081/api/orders/user
Authorization: Bearer <your_jwt_token>
```

#### 1.4 Update Order Status
```http
PUT http://localhost:8081/api/orders/1001/status?status=CONFIRMED
Authorization: Bearer <your_jwt_token>
```

**Status Values**: `PENDING`, `CONFIRMED`, `PREPARING`, `OUT_FOR_DELIVERY`, `DELIVERED`, `CANCELLED`

### 2. Group Orders

#### 2.1 Create Group Order
```http
POST http://localhost:8081/api/orders/group
Content-Type: application/json
Authorization: Bearer <your_jwt_token>

{
  "initiatorUserId": 1,
  "groupName": "Office Lunch Order",
  "restaurantId": 1,
  "deadline": "2025-10-16T11:30:00",
  "minParticipants": 3,
  "maxParticipants": 10,
  "deliveryAddress": "Office Building, 456 Business Ave",
  "splitMethod": "EQUAL",
  "deliveryInstructions": "Call when arrived - ask for reception"
}
```

#### 2.2 Join Group Order
```http
POST http://localhost:8081/api/orders/group/GROUP123/join?userId=2
Authorization: Bearer <your_jwt_token>
```

#### 2.3 Add Items to Group Order
```http
POST http://localhost:8081/api/orders/group/GROUP123/items?userId=2
Content-Type: application/json
Authorization: Bearer <your_jwt_token>

[
  {
    "menuItemId": 103,
    "itemName": "Chicken Burger",
    "quantity": 1,
    "price": 11.99,
    "specialInstructions": "No pickles"
  }
]
```

### 3. Scheduled Orders

#### 3.1 Schedule Order
```http
POST http://localhost:8081/api/orders/scheduled
Content-Type: application/json
Authorization: Bearer <your_jwt_token>

{
  "userId": 1,
  "restaurantId": 1,
  "scheduledTime": "2025-10-17T19:00:00",
  "recurringType": "WEEKLY",
  "recurringDays": ["FRIDAY"],
  "endDate": "2025-12-31T00:00:00",
  "orderDetails": {
    "items": [
      {
        "menuItemId": 101,
        "itemName": "Friday Special",
        "quantity": 1,
        "price": 15.99
      }
    ],
    "totalAmount": 15.99,
    "deliveryAddress": "123 Main St, City, State 12345"
  }
}
```

#### 3.2 Get User Scheduled Orders
```http
GET http://localhost:8081/api/orders/scheduled/1
Authorization: Bearer <your_jwt_token>
```

#### 3.3 Cancel Scheduled Order
```http
DELETE http://localhost:8081/api/orders/scheduled/SCHED123
Authorization: Bearer <your_jwt_token>
```

### 4. Multi-Restaurant Orders

#### 4.1 Create Multi-Restaurant Order
```http
POST http://localhost:8081/api/orders/multi-restaurant
Content-Type: application/json
Authorization: Bearer <your_jwt_token>

{
  "userId": 1,
  "deliveryAddress": "123 Main St, City, State 12345",
  "subOrders": [
    {
      "restaurantId": 1,
      "items": [
        {
          "menuItemId": 101,
          "itemName": "Pizza",
          "quantity": 1,
          "price": 12.99
        }
      ],
      "subTotal": 12.99
    },
    {
      "restaurantId": 2,
      "items": [
        {
          "menuItemId": 201,
          "itemName": "Sushi Roll",
          "quantity": 2,
          "price": 8.50
        }
      ],
      "subTotal": 17.00
    }
  ],
  "totalAmount": 29.99,
  "coordinatedDelivery": true,
  "targetDeliveryTime": "2025-10-16T19:30:00"
}
```

#### 4.2 Get Multi-Restaurant Order
```http
GET http://localhost:8081/api/orders/multi-restaurant/MULTI123
Authorization: Bearer <your_jwt_token>
```

#### 4.3 Get Multi-Restaurant Order Tracking
```http
GET http://localhost:8081/api/orders/multi-restaurant/MULTI123/tracking
Authorization: Bearer <your_jwt_token>
```

## Testing Scenarios in Postman

### Scenario 1: Complete Order Flow
1. **Login** to get JWT token
2. **Create Order** with valid data
3. **Get Order** details to verify creation
4. **Update Status** to CONFIRMED
5. **Get User Orders** to see all orders

### Scenario 2: Group Order Flow
1. **Create Group Order** as initiator
2. **Join Group Order** with different user
3. **Add Items** to the group order
4. **Get Group Order** details

### Scenario 3: Error Testing
1. **Create Order** without authentication (should return 401)
2. **Create Order** with invalid restaurant ID
3. **Get Order** with non-existent order ID
4. **Update Status** with invalid status value

## Environment Variables for Postman

Create these environment variables in Postman:
- `base_url`: `http://localhost:8081`
- `auth_url`: `http://localhost:8080`
- `jwt_token`: (set after login)

## Common HTTP Status Codes

- **200 OK**: Successful request
- **201 Created**: Resource created successfully
- **400 Bad Request**: Invalid request data
- **401 Unauthorized**: Missing or invalid JWT token
- **403 Forbidden**: Insufficient permissions
- **404 Not Found**: Resource not found
- **500 Internal Server Error**: Server error

## Sample Test Data

### Valid User Credentials:
```json
{
  "email": "john.doe@example.com",
  "password": "password123"
}
```

### Valid Restaurant IDs:
- 1: Italian Restaurant
- 2: Japanese Restaurant  
- 3: Mexican Restaurant

### Valid Menu Item IDs:
- 101-110: Italian dishes
- 201-210: Japanese dishes
- 301-310: Mexican dishes

## Notes for Testing

1. **Authentication**: Always include the JWT token in the Authorization header
2. **Order Status Flow**: PENDING → CONFIRMED → PREPARING → OUT_FOR_DELIVERY → DELIVERED
3. **Time Format**: Use ISO 8601 format (YYYY-MM-DDTHH:mm:ss)
4. **Decimal Values**: Use proper decimal format for prices (e.g., 12.99)
5. **User ID**: Will be automatically extracted from JWT token for most endpoints

## Troubleshooting

- **401 Unauthorized**: Check if JWT token is valid and not expired
- **404 Not Found**: Verify order ID exists and belongs to the authenticated user
- **400 Bad Request**: Check request body format and required fields
- **Database Connection**: Ensure MySQL database is running and accessible