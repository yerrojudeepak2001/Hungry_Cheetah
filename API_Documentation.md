# Food Delivery Application API Documentation

This document provides a comprehensive list of all microservices and their API endpoints in the Hungry Cheetah food delivery application.

## Table of Contents

1. [User Service](#1-user-service)
2. [Auth Service](#2-auth-service)
3. [Restaurant Service](#3-restaurant-service)
4. [Order Service](#4-order-service)
5. [Cart Service](#5-cart-service)
6. [Payment Service](#6-payment-service)
7. [Delivery Service](#7-delivery-service)
8. [Notification Service](#8-notification-service)
9. [Search Service](#9-search-service)
10. [Loyalty Service](#10-loyalty-service)
11. [Analytics Service](#11-analytics-service)
12. [Admin Service](#12-admin-service)
13. [AI Service](#13-ai-service)
14. [Promotion Service](#14-promotion-service)
15. [Tracking Service](#15-tracking-service)
16. [Social Service](#16-social-service)
17. [Subscription Service](#17-subscription-service)

---

## 1. User Service

User service handles user profiles, preferences, addresses, and favorites.

### Base URL: `/api/users`

#### User Management

| Method | Endpoint               | Description                         | Request Body           | Response                     |
|--------|------------------------|-------------------------------------|------------------------|-----------------------------|
| POST   | `/register`            | Register a new user                 | User object            | Registered user details      |
| GET    | `/{userId}`            | Get user profile                    | -                      | User profile details         |
| PUT    | `/{userId}`            | Update user profile                 | User object            | Updated user details         |

**Example Request - Register User:**
```json
POST /api/users/register
{
  "username": "john_doe",
  "email": "john.doe@example.com",
  "password": "securePassword123",
  "firstName": "John",
  "lastName": "Doe",
  "phoneNumber": "+1234567890"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "User registered successfully",
  "data": {
    "id": 123,
    "username": "john_doe",
    "email": "john.doe@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "phoneNumber": "+1234567890",
    "createdAt": "2025-10-13T10:30:00Z"
  }
}
```

#### User Preferences

| Method | Endpoint                          | Description                     | Request Body           | Response                 |
|--------|------------------------------------|----------------------------------|------------------------|--------------------------|
| POST   | `/{userId}/preferences`            | Set user preferences             | UserPreference object  | Updated preferences      |
| GET    | `/{userId}/preferences`            | Get user preferences             | -                      | User preferences details |
| POST   | `/{userId}/dietary-restrictions`   | Set dietary restrictions         | List of strings        | Updated restrictions     |
| GET    | `/{userId}/dietary-restrictions`   | Get dietary restrictions         | -                      | List of restrictions     |

**Example Request - Set User Preferences:**
```json
POST /api/users/123/preferences
{
  "cuisinePreferences": ["Italian", "Chinese", "Mexican"],
  "spiceLevelPreference": "MEDIUM",
  "dietaryRestrictions": ["VEGETARIAN"],
  "allergens": ["NUTS", "SHELLFISH"]
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Preferences updated successfully",
  "data": {
    "id": 45,
    "userId": 123,
    "cuisinePreferences": ["Italian", "Chinese", "Mexican"],
    "spiceLevelPreference": "MEDIUM",
    "dietaryRestrictions": ["VEGETARIAN"],
    "allergens": ["NUTS", "SHELLFISH"],
    "updatedAt": "2025-10-13T11:15:00Z"
  }
}
```

#### Address Management

| Method | Endpoint                          | Description                     | Request Body           | Response                 |
|--------|------------------------------------|----------------------------------|------------------------|--------------------------|
| POST   | `/{userId}/addresses`              | Add a new address                | Address object         | Added address details    |
| GET    | `/{userId}/addresses`              | Get all user addresses           | -                      | List of addresses        |
| PUT    | `/{userId}/addresses/{addressId}`  | Update an address                | Address object         | Updated address details  |
| DELETE | `/{userId}/addresses/{addressId}`  | Delete an address                | -                      | Success message          |

**Example Request - Add Address:**
```json
POST /api/users/123/addresses
{
  "type": "HOME",
  "addressLine1": "123 Main Street",
  "addressLine2": "Apt 4B",
  "city": "New York",
  "state": "NY",
  "zipCode": "10001",
  "country": "USA",
  "isDefault": true,
  "latitude": 40.7128,
  "longitude": -74.0060
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Address added successfully",
  "data": {
    "id": 78,
    "userId": 123,
    "type": "HOME",
    "addressLine1": "123 Main Street",
    "addressLine2": "Apt 4B",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA",
    "isDefault": true,
    "latitude": 40.7128,
    "longitude": -74.0060,
    "createdAt": "2025-10-13T11:30:00Z"
  }
}
```

#### User History and Favorites

| Method | Endpoint                                         | Description                     | Request Body           | Response                 |
|--------|--------------------------------------------------|----------------------------------|------------------------|--------------------------|
| GET    | `/{userId}/order-history`                         | Get user order history           | -                      | List of orders           |
| GET    | `/{userId}/favorite-restaurants`                  | Get favorite restaurants         | -                      | List of restaurants      |
| POST   | `/{userId}/favorite-restaurants/{restaurantId}`   | Add a restaurant to favorites    | -                      | Success message          |
| DELETE | `/{userId}/favorite-restaurants/{restaurantId}`   | Remove restaurant from favorites | -                      | Success message          |

**Example Request - Add Favorite Restaurant:**
```json
POST /api/users/123/favorite-restaurants/456
```

**Example Response:**
```json
{
  "success": true,
  "message": "Restaurant added to favorites",
  "data": null
}
```

---

## 2. Auth Service

Authentication service handles user registration, login, and token management.

### Base URL: `/api/auth`

| Method | Endpoint          | Description                     | Request Body               | Response               |
|--------|--------------------|----------------------------------|-----------------------------|------------------------|
| POST   | `/register`        | Register a new user              | RegisterRequest object      | JWT Token              |
| POST   | `/login`           | Login user                       | LoginRequest object         | JWT Token              |
| POST   | `/refresh-token`   | Refresh JWT token                | RefreshTokenRequest object  | New JWT Token          |
| POST   | `/logout`          | Logout user                      | -                           | Success message        |

**Example Request - Login:**
```json
POST /api/auth/login
{
  "email": "john.doe@example.com",
  "password": "securePassword123"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Login successful",
  "data": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjMiLCJuYW1lIjoiSm9obiBEb2UiLCJpYXQiOjE2MzQ2NTk4MDB9.ZWYzMzA4NjFkNzJlZjE2ZjcwOTU3"
}
```

---

## 3. Restaurant Service

Restaurant service handles restaurant profiles, menus, reviews, and analytics.

### Base URL: `/api/restaurants`

#### Restaurant Management

| Method | Endpoint               | Description                     | Request Body               | Response                      |
|--------|------------------------|----------------------------------|-----------------------------|-------------------------------|
| POST   | `/`                    | Register a new restaurant        | Restaurant object          | Registered restaurant details |
| GET    | `/{restaurantId}`      | Get restaurant details           | -                           | Restaurant details            |
| PUT    | `/{restaurantId}`      | Update restaurant details        | Restaurant object          | Updated restaurant details    |

**Example Request - Register Restaurant:**
```json
POST /api/restaurants
{
  "name": "Bella Italia",
  "description": "Authentic Italian cuisine",
  "address": {
    "addressLine1": "456 Food Street",
    "city": "Chicago",
    "state": "IL",
    "zipCode": "60601",
    "country": "USA",
    "latitude": 41.8781,
    "longitude": -87.6298
  },
  "cuisineType": ["ITALIAN"],
  "openingHours": {
    "MONDAY": {"open": "11:00", "close": "22:00"},
    "TUESDAY": {"open": "11:00", "close": "22:00"},
    "WEDNESDAY": {"open": "11:00", "close": "22:00"},
    "THURSDAY": {"open": "11:00", "close": "22:00"},
    "FRIDAY": {"open": "11:00", "close": "23:00"},
    "SATURDAY": {"open": "12:00", "close": "23:00"},
    "SUNDAY": {"open": "12:00", "close": "22:00"}
  },
  "contactPhone": "+1987654321",
  "contactEmail": "info@bellaitalia.com"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Restaurant registered successfully",
  "data": {
    "id": 456,
    "name": "Bella Italia",
    "description": "Authentic Italian cuisine",
    "address": {
      "addressLine1": "456 Food Street",
      "city": "Chicago",
      "state": "IL",
      "zipCode": "60601",
      "country": "USA",
      "latitude": 41.8781,
      "longitude": -87.6298
    },
    "cuisineType": ["ITALIAN"],
    "openingHours": {
      "MONDAY": {"open": "11:00", "close": "22:00"},
      "TUESDAY": {"open": "11:00", "close": "22:00"},
      "WEDNESDAY": {"open": "11:00", "close": "22:00"},
      "THURSDAY": {"open": "11:00", "close": "22:00"},
      "FRIDAY": {"open": "11:00", "close": "23:00"},
      "SATURDAY": {"open": "12:00", "close": "23:00"},
      "SUNDAY": {"open": "12:00", "close": "22:00"}
    },
    "contactPhone": "+1987654321",
    "contactEmail": "info@bellaitalia.com",
    "rating": 0,
    "createdAt": "2025-10-13T12:00:00Z"
  }
}
```

#### Menu Management

| Method | Endpoint                               | Description                     | Request Body           | Response                 |
|--------|----------------------------------------|----------------------------------|------------------------|--------------------------|
| POST   | `/{restaurantId}/menu-items`            | Add menu item                    | MenuItem object        | Added menu item details  |
| GET    | `/{restaurantId}/menu`                  | Get restaurant menu              | -                      | Complete menu            |
| PUT    | `/{restaurantId}/menu-items/{itemId}`   | Update menu item                 | MenuItem object        | Updated menu item        |
| DELETE | `/{restaurantId}/menu-items/{itemId}`   | Remove menu item                 | -                      | Success message          |
| POST   | `/{restaurantId}/special-menus`         | Add special menu                 | SpecialMenu object     | Added special menu       |
| GET    | `/{restaurantId}/special-menus`         | Get special menus                | -                      | List of special menus    |

**Example Request - Add Menu Item:**
```json
POST /api/restaurants/456/menu-items
{
  "name": "Margherita Pizza",
  "description": "Classic tomato and mozzarella pizza",
  "price": 12.99,
  "category": "PIZZA",
  "ingredients": ["Dough", "Tomato Sauce", "Mozzarella", "Basil"],
  "allergens": ["GLUTEN", "DAIRY"],
  "spiceLevel": "MILD",
  "vegetarian": true,
  "vegan": false,
  "featured": true,
  "imageUrl": "https://images.example.com/margherita.jpg"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Menu item added successfully",
  "data": {
    "id": 789,
    "restaurantId": 456,
    "name": "Margherita Pizza",
    "description": "Classic tomato and mozzarella pizza",
    "price": 12.99,
    "category": "PIZZA",
    "ingredients": ["Dough", "Tomato Sauce", "Mozzarella", "Basil"],
    "allergens": ["GLUTEN", "DAIRY"],
    "spiceLevel": "MILD",
    "vegetarian": true,
    "vegan": false,
    "featured": true,
    "imageUrl": "https://images.example.com/margherita.jpg",
    "createdAt": "2025-10-13T12:15:00Z"
  }
}
```

#### Reviews and Ratings

| Method | Endpoint                          | Description                     | Request Body           | Response                 |
|--------|------------------------------------|----------------------------------|------------------------|--------------------------|
| POST   | `/{restaurantId}/reviews`           | Add review                       | Review object          | Added review             |
| GET    | `/{restaurantId}/reviews`           | Get restaurant reviews           | page, size parameters  | Paginated reviews        |

**Example Request - Add Review:**
```json
POST /api/restaurants/456/reviews
{
  "userId": 123,
  "rating": 4.5,
  "comment": "Great food and excellent service! The Margherita pizza was delicious.",
  "orderIds": [55667]
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Review added successfully",
  "data": {
    "id": 321,
    "restaurantId": 456,
    "userId": 123,
    "rating": 4.5,
    "comment": "Great food and excellent service! The Margherita pizza was delicious.",
    "orderIds": [55667],
    "createdAt": "2025-10-13T12:30:00Z"
  }
}
```

#### Restaurant Analytics

| Method | Endpoint                                  | Description                     | Request Body              | Response                 |
|--------|-------------------------------------------|----------------------------------|-----------------------------|--------------------------|
| GET    | `/{restaurantId}/analytics/orders`         | Get order analytics              | timeFrame parameter        | Order analytics data     |
| GET    | `/{restaurantId}/analytics/popular-items`  | Get popular items                | timeFrame parameter        | List of popular items    |

**Example Request - Get Order Analytics:**
```
GET /api/restaurants/456/analytics/orders?timeFrame=LAST_MONTH
```

**Example Response:**
```json
{
  "success": true,
  "message": "Order analytics fetched successfully",
  "data": {
    "totalOrders": 342,
    "totalRevenue": 5678.90,
    "averageOrderValue": 16.60,
    "peakOrderTimes": {
      "MONDAY": "18:00-19:00",
      "TUESDAY": "18:00-19:00",
      "WEDNESDAY": "18:00-19:00",
      "THURSDAY": "18:00-19:00",
      "FRIDAY": "19:00-20:00",
      "SATURDAY": "19:00-20:00",
      "SUNDAY": "17:00-18:00"
    },
    "timeFrame": "LAST_MONTH"
  }
}
```

#### AR Menu and Virtual Tour

| Method | Endpoint                          | Description                     | Request Body           | Response                 |
|--------|------------------------------------|----------------------------------|------------------------|--------------------------|
| POST   | `/{restaurantId}/ar-menu`          | Upload AR menu                   | ARMenu object          | Uploaded AR menu         |
| GET    | `/{restaurantId}/ar-menu`          | Get AR menu                      | -                      | AR menu data             |
| POST   | `/{restaurantId}/virtual-tour`     | Upload virtual tour              | VirtualTour object     | Uploaded virtual tour    |
| GET    | `/{restaurantId}/virtual-tour`     | Get virtual tour                 | -                      | Virtual tour data        |

**Example Request - Upload AR Menu:**
```json
POST /api/restaurants/456/ar-menu
{
  "arEnabled": true,
  "modelUrls": [
    {
      "itemId": 789,
      "modelUrl": "https://ar-models.example.com/pizza-margherita.glb"
    }
  ],
  "appCompatibility": ["IOS", "ANDROID"]
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "AR menu uploaded successfully",
  "data": {
    "id": 45,
    "restaurantId": 456,
    "arEnabled": true,
    "modelUrls": [
      {
        "itemId": 789,
        "modelUrl": "https://ar-models.example.com/pizza-margherita.glb"
      }
    ],
    "appCompatibility": ["IOS", "ANDROID"],
    "createdAt": "2025-10-13T12:45:00Z"
  }
}
```

---

## 4. Order Service

Order service handles creation and management of orders including regular, group, scheduled, and multi-restaurant orders.

### Base URL: `/api/orders`

#### Regular Orders

| Method | Endpoint              | Description                     | Request Body           | Response                 |
|--------|------------------------|----------------------------------|------------------------|--------------------------|
| POST   | `/`                    | Create a new order                | OrderDTO object        | Created order details    |
| GET    | `/{orderId}`           | Get order status                  | -                      | Order details            |
| PUT    | `/{orderId}/status`    | Update order status               | status parameter       | Success message          |

**Example Request - Create Order:**
```json
POST /api/orders
{
  "userId": 123,
  "restaurantId": 456,
  "deliveryAddress": {
    "id": 78,
    "addressLine1": "123 Main Street",
    "city": "New York"
  },
  "items": [
    {
      "itemId": 789,
      "quantity": 2,
      "specialInstructions": "Extra cheese please"
    }
  ],
  "paymentMethod": "CREDIT_CARD",
  "paymentDetails": {
    "cardId": "card_123456789"
  },
  "deliveryInstructions": "Please leave at the door",
  "deliveryTime": "ASAP"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Order created successfully",
  "data": {
    "id": 55667,
    "userId": 123,
    "restaurantId": 456,
    "status": "PLACED",
    "items": [
      {
        "itemId": 789,
        "name": "Margherita Pizza",
        "price": 12.99,
        "quantity": 2,
        "specialInstructions": "Extra cheese please"
      }
    ],
    "subtotal": 25.98,
    "tax": 2.08,
    "deliveryFee": 2.99,
    "tip": 0,
    "total": 31.05,
    "createdAt": "2025-10-13T13:00:00Z",
    "estimatedDeliveryTime": "2025-10-13T13:45:00Z",
    "paymentStatus": "PENDING"
  }
}
```

#### Group Orders

| Method | Endpoint                          | Description                     | Request Body                 | Response                 |
|--------|------------------------------------|----------------------------------|-----------------------------|--------------------------|
| POST   | `/group`                           | Create a group order             | GroupOrder object           | Created group order      |
| POST   | `/group/{orderId}/join`            | Join a group order               | userId parameter            | Updated group order      |
| POST   | `/group/{orderId}/items`           | Add items to group order         | List of GroupOrderItem      | Updated group order      |

**Example Request - Create Group Order:**
```json
POST /api/orders/group
{
  "initiatorId": 123,
  "restaurantId": 456,
  "deliveryAddress": {
    "id": 78,
    "addressLine1": "123 Main Street",
    "city": "New York"
  },
  "expiresAt": "2025-10-13T14:30:00Z",
  "paymentType": "SPLIT_EQUAL"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Group order created successfully",
  "data": {
    "id": "grp-123456",
    "initiatorId": 123,
    "restaurantId": 456,
    "status": "COLLECTING",
    "participants": [123],
    "items": [],
    "paymentType": "SPLIT_EQUAL",
    "deliveryAddress": {
      "id": 78,
      "addressLine1": "123 Main Street",
      "city": "New York"
    },
    "expiresAt": "2025-10-13T14:30:00Z",
    "createdAt": "2025-10-13T13:30:00Z",
    "shareLink": "https://foodapp.com/group/grp-123456",
    "total": 0.0
  }
}
```

#### Scheduled Orders

| Method | Endpoint                       | Description                     | Request Body             | Response                   |
|--------|---------------------------------|----------------------------------|---------------------------|----------------------------|
| POST   | `/scheduled`                    | Schedule an order                | ScheduledOrder object     | Scheduled order details    |
| GET    | `/scheduled/{userId}`           | Get user's scheduled orders      | -                         | List of scheduled orders   |
| DELETE | `/scheduled/{orderId}`          | Cancel a scheduled order         | -                         | Success message            |

**Example Request - Schedule Order:**
```json
POST /api/orders/scheduled
{
  "userId": 123,
  "restaurantId": 456,
  "scheduledTime": "2025-10-14T12:00:00Z",
  "recurrence": "WEEKLY",
  "items": [
    {
      "itemId": 789,
      "quantity": 2,
      "specialInstructions": ""
    }
  ],
  "deliveryAddress": {
    "id": 78
  },
  "paymentMethod": "SAVED_CARD",
  "paymentDetails": {
    "cardId": "card_123456789"
  }
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Order scheduled successfully",
  "data": {
    "id": "sch-123456",
    "userId": 123,
    "restaurantId": 456,
    "scheduledTime": "2025-10-14T12:00:00Z",
    "recurrence": "WEEKLY",
    "items": [
      {
        "itemId": 789,
        "name": "Margherita Pizza",
        "quantity": 2,
        "specialInstructions": ""
      }
    ],
    "deliveryAddress": {
      "id": 78,
      "addressLine1": "123 Main Street",
      "city": "New York"
    },
    "estimatedTotal": 31.05,
    "createdAt": "2025-10-13T14:00:00Z"
  }
}
```

#### Multi-Restaurant Orders

| Method | Endpoint                                   | Description                     | Request Body               | Response                     |
|--------|-------------------------------------------|----------------------------------|-----------------------------|------------------------------|
| POST   | `/multi-restaurant`                        | Create multi-restaurant order    | MultiRestaurantOrder object | Created order details        |
| GET    | `/multi-restaurant/{orderId}`              | Get multi-restaurant order       | -                           | Multi-restaurant order       |
| GET    | `/multi-restaurant/{orderId}/tracking`     | Get multi-restaurant tracking    | -                           | Order tracking details       |

**Example Request - Create Multi-Restaurant Order:**
```json
POST /api/orders/multi-restaurant
{
  "userId": 123,
  "deliveryAddress": {
    "id": 78
  },
  "orders": [
    {
      "restaurantId": 456,
      "items": [
        {
          "itemId": 789,
          "quantity": 2
        }
      ]
    },
    {
      "restaurantId": 457,
      "items": [
        {
          "itemId": 790,
          "quantity": 1
        }
      ]
    }
  ],
  "paymentMethod": "CREDIT_CARD",
  "paymentDetails": {
    "cardId": "card_123456789"
  },
  "deliveryInstructions": "Please leave at the door"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Multi-restaurant order created successfully",
  "data": {
    "id": "multi-123456",
    "userId": 123,
    "status": "PROCESSING",
    "orders": [
      {
        "orderId": "55668",
        "restaurantId": 456,
        "status": "PLACED"
      },
      {
        "orderId": "55669",
        "restaurantId": 457,
        "status": "PLACED"
      }
    ],
    "deliveryAddress": {
      "id": 78,
      "addressLine1": "123 Main Street",
      "city": "New York"
    },
    "subtotal": 42.97,
    "tax": 3.44,
    "deliveryFee": 4.99,
    "tip": 0,
    "total": 51.40,
    "createdAt": "2025-10-13T14:30:00Z",
    "estimatedDeliveryTime": "2025-10-13T15:30:00Z"
  }
}
```

---

## 5. Cart Service

Cart service manages user shopping carts and saved items.

### Base URL: `/api/carts`

| Method | Endpoint                       | Description                     | Request Body                | Response                 |
|--------|---------------------------------|----------------------------------|----------------------------|--------------------------|
| GET    | `/{userId}`                     | Get user's cart                  | -                           | Cart details             |
| POST   | `/{userId}/items`               | Add item to cart                 | CartItem object             | Updated cart             |
| PUT    | `/{userId}/items/{itemId}`      | Update cart item quantity        | quantity parameter          | Updated cart             |
| DELETE | `/{userId}/items/{itemId}`      | Remove item from cart            | -                           | Updated cart             |
| POST   | `/{userId}/saved-for-later`     | Save item for later              | CartItem object             | Saved items list         |
| GET    | `/{userId}/saved-for-later`     | Get saved items                  | -                           | Saved items list         |

**Example Request - Add Item to Cart:**
```json
POST /api/carts/123/items
{
  "itemId": 789,
  "restaurantId": 456,
  "quantity": 2,
  "specialInstructions": "Extra cheese please"
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Item added to cart",
  "data": {
    "userId": 123,
    "restaurantId": 456,
    "items": [
      {
        "itemId": 789,
        "name": "Margherita Pizza",
        "price": 12.99,
        "quantity": 2,
        "specialInstructions": "Extra cheese please"
      }
    ],
    "subtotal": 25.98,
    "estimatedTax": 2.08,
    "estimatedTotal": 28.06,
    "lastUpdated": "2025-10-13T15:00:00Z"
  }
}
```

---

## 6. Payment Service

Payment service manages payment methods, transactions, and refunds.

### Base URL: `/api/payments`

| Method | Endpoint                          | Description                     | Request Body                   | Response                     |
|--------|------------------------------------|----------------------------------|--------------------------------|------------------------------|
| POST   | `/methods`                         | Add payment method               | PaymentMethod object           | Added payment method         |
| GET    | `/methods/{userId}`                | Get user's payment methods        | -                               | List of payment methods      |
| DELETE | `/methods/{paymentMethodId}`       | Delete payment method            | -                               | Success message              |
| POST   | `/process`                         | Process payment                  | PaymentRequest object          | Payment confirmation         |
| GET    | `/transactions/{userId}`           | Get user's transactions          | -                               | List of transactions         |
| POST   | `/refunds`                         | Request a refund                 | RefundRequest object           | Refund confirmation          |

**Example Request - Process Payment:**
```json
POST /api/payments/process
{
  "orderId": 55667,
  "userId": 123,
  "amount": 31.05,
  "paymentMethod": "CREDIT_CARD",
  "paymentDetails": {
    "cardId": "card_123456789"
  }
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Payment processed successfully",
  "data": {
    "transactionId": "txn_987654321",
    "orderId": 55667,
    "userId": 123,
    "amount": 31.05,
    "status": "COMPLETED",
    "timestamp": "2025-10-13T15:15:00Z"
  }
}
```

---

## 7. Delivery Service

Delivery service manages delivery assignments, tracking, and status updates.

### Base URL: `/api/delivery`

| Method | Endpoint                             | Description                     | Request Body                   | Response                     |
|--------|--------------------------------------|----------------------------------|--------------------------------|------------------------------|
| GET    | `/{orderId}`                          | Get delivery status               | -                               | Delivery status details       |
| PUT    | `/{orderId}/status`                   | Update delivery status            | status parameter               | Updated delivery status       |
| POST   | `/drivers/assign`                     | Assign driver to delivery         | AssignDriverRequest object     | Assignment confirmation       |
| GET    | `/drivers/{driverId}/active`          | Get driver's active deliveries    | -                               | List of active deliveries     |
| PUT    | `/drivers/{driverId}/location`        | Update driver location            | Location object                | Updated driver details        |

**Example Request - Update Delivery Status:**
```json
PUT /api/delivery/55667/status?status=IN_TRANSIT
```

**Example Response:**
```json
{
  "success": true,
  "message": "Delivery status updated successfully",
  "data": {
    "deliveryId": "del-123456",
    "orderId": 55667,
    "status": "IN_TRANSIT",
    "driverId": 789,
    "driverName": "Mike Johnson",
    "driverPhone": "+1234567890",
    "estimatedArrival": "2025-10-13T15:45:00Z",
    "currentLocation": {
      "latitude": 40.7128,
      "longitude": -74.0060
    },
    "lastUpdated": "2025-10-13T15:30:00Z"
  }
}
```

---

## 8. Notification Service

Notification service handles sending and managing user notifications.

### Base URL: `/api/notifications`

| Method | Endpoint                             | Description                     | Request Body                     | Response                       |
|--------|--------------------------------------|----------------------------------|----------------------------------|--------------------------------|
| POST   | `/send`                               | Send notification                | NotificationRequest object       | Notification confirmation       |
| GET    | `/{userId}`                           | Get user's notifications         | -                                 | List of notifications           |
| PUT    | `/{notificationId}/read`              | Mark notification as read        | -                                 | Updated notification            |
| POST   | `/preferences/{userId}`               | Update notification preferences  | NotificationPreferences object   | Updated preferences             |
| GET    | `/preferences/{userId}`               | Get notification preferences     | -                                 | Notification preferences        |

**Example Request - Send Notification:**
```json
POST /api/notifications/send
{
  "userId": 123,
  "type": "ORDER_STATUS",
  "title": "Order Status Update",
  "message": "Your order #55667 is being prepared",
  "data": {
    "orderId": 55667,
    "status": "PREPARING"
  },
  "channels": ["PUSH", "EMAIL"]
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Notification sent successfully",
  "data": {
    "id": "notif-123456",
    "userId": 123,
    "type": "ORDER_STATUS",
    "title": "Order Status Update",
    "message": "Your order #55667 is being prepared",
    "read": false,
    "sentAt": "2025-10-13T15:45:00Z",
    "channels": ["PUSH", "EMAIL"]
  }
}
```

---

## 9. Search Service

Search service handles food, restaurant, and dish searching with filters and recommendations.

### Base URL: `/api/search`

| Method | Endpoint                     | Description                     | Request Body                     | Response                       |
|--------|------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/restaurants`                | Search restaurants               | Query parameters                  | List of restaurants            |
| GET    | `/dishes`                     | Search dishes                    | Query parameters                  | List of dishes                 |
| GET    | `/trending`                   | Get trending searches            | -                                 | List of trending items         |
| GET    | `/nearby`                     | Search nearby restaurants        | location parameters               | List of nearby restaurants     |
| GET    | `/filters`                    | Get available search filters     | -                                 | List of filters                |

**Example Request - Search Restaurants:**
```
GET /api/search/restaurants?query=pizza&cuisine=italian&price=$$&rating=4&distance=5&open=true&lat=40.7128&lng=-74.0060
```

**Example Response:**
```json
{
  "success": true,
  "message": "Search results fetched successfully",
  "data": {
    "results": [
      {
        "id": 456,
        "name": "Bella Italia",
        "cuisineType": ["ITALIAN"],
        "rating": 4.5,
        "distance": 2.3,
        "estimatedDeliveryTime": 30,
        "priceLevel": "$$",
        "featuredItems": [
          {
            "id": 789,
            "name": "Margherita Pizza",
            "price": 12.99,
            "imageUrl": "https://images.example.com/margherita.jpg"
          }
        ]
      },
      {
        "id": 457,
        "name": "Papa's Pizzeria",
        "cuisineType": ["ITALIAN", "AMERICAN"],
        "rating": 4.2,
        "distance": 3.1,
        "estimatedDeliveryTime": 35,
        "priceLevel": "$$",
        "featuredItems": [
          {
            "id": 790,
            "name": "Pepperoni Pizza",
            "price": 14.99,
            "imageUrl": "https://images.example.com/pepperoni.jpg"
          }
        ]
      }
    ],
    "totalResults": 2,
    "filters": {
      "cuisine": ["ITALIAN", "AMERICAN"],
      "price": ["$$"]
    }
  }
}
```

---

## 10. Loyalty Service

Loyalty service manages user rewards, points, and loyalty programs.

### Base URL: `/api/loyalty`

| Method | Endpoint                     | Description                     | Request Body                     | Response                       |
|--------|------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/{userId}/points`            | Get user's loyalty points        | -                                 | Loyalty points details          |
| POST   | `/{userId}/points/add`        | Add loyalty points               | PointsAddRequest object          | Updated loyalty points          |
| GET    | `/{userId}/rewards`           | Get available rewards            | -                                 | List of rewards                 |
| POST   | `/{userId}/rewards/redeem`    | Redeem reward                    | RewardRedeemRequest object       | Redemption confirmation         |
| GET    | `/tiers`                      | Get loyalty program tiers        | -                                 | List of loyalty tiers          |
| GET    | `/{userId}/tier`              | Get user's current tier          | -                                 | User tier details              |

**Example Request - Redeem Reward:**
```json
POST /api/loyalty/123/rewards/redeem
{
  "rewardId": "rwd-123456",
  "orderId": 55667
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Reward redeemed successfully",
  "data": {
    "id": "rdm-123456",
    "userId": 123,
    "rewardId": "rwd-123456",
    "rewardName": "Free Delivery",
    "pointsUsed": 500,
    "remainingPoints": 250,
    "appliedToOrderId": 55667,
    "redeemedAt": "2025-10-13T16:00:00Z",
    "expiresAt": "2025-10-20T16:00:00Z"
  }
}
```

---

## 11. Analytics Service

Analytics service provides insights and reports for both users and restaurants.

### Base URL: `/api/analytics`

| Method | Endpoint                           | Description                     | Request Body                     | Response                       |
|--------|-----------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/user/{userId}/spending`          | Get user spending analytics      | timeFrame parameter              | User spending analytics         |
| GET    | `/user/{userId}/favorites`         | Get user favorite analysis       | -                                 | User favorites analytics        |
| GET    | `/restaurant/{restaurantId}/sales` | Get restaurant sales analytics   | timeFrame parameter              | Restaurant sales analytics      |
| GET    | `/trends/cuisines`                 | Get trending cuisines            | location parameter                | Cuisine trends                  |
| GET    | `/trends/dishes`                   | Get trending dishes              | location parameter                | Dish trends                     |

**Example Request - Get User Spending Analytics:**
```
GET /api/analytics/user/123/spending?timeFrame=LAST_6_MONTHS
```

**Example Response:**
```json
{
  "success": true,
  "message": "User spending analytics fetched successfully",
  "data": {
    "userId": 123,
    "totalSpent": 850.75,
    "averageOrderValue": 35.45,
    "orderFrequency": "2.3 per week",
    "topRestaurants": [
      {
        "restaurantId": 456,
        "name": "Bella Italia",
        "totalSpent": 320.50,
        "orderCount": 10
      },
      {
        "restaurantId": 457,
        "name": "Papa's Pizzeria",
        "totalSpent": 245.75,
        "orderCount": 7
      }
    ],
    "topCategories": [
      {
        "category": "PIZZA",
        "totalSpent": 410.25,
        "orderCount": 12
      },
      {
        "category": "PASTA",
        "totalSpent": 215.50,
        "orderCount": 6
      }
    ],
    "monthlyBreakdown": [
      {
        "month": "May 2025",
        "totalSpent": 145.25,
        "orderCount": 4
      },
      {
        "month": "June 2025",
        "totalSpent": 135.50,
        "orderCount": 4
      }
      // Additional months...
    ],
    "timeFrame": "LAST_6_MONTHS"
  }
}
```

---

## 12. Admin Service

Admin service provides admin-level operations and dashboard access.

### Base URL: `/api/admin`

| Method | Endpoint                           | Description                     | Request Body                     | Response                       |
|--------|-----------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/dashboard/overview`              | Get admin dashboard overview     | -                                 | Dashboard overview data         |
| GET    | `/restaurants`                     | Get all restaurants              | pagination parameters            | List of restaurants             |
| PUT    | `/restaurants/{restaurantId}/verify` | Verify a restaurant            | verification details             | Updated restaurant details       |
| GET    | `/users`                           | Get all users                    | pagination parameters            | List of users                   |
| PUT    | `/users/{userId}/block`            | Block/unblock user               | status parameter                 | Updated user status             |
| GET    | `/orders/recent`                   | Get recent orders                | -                                 | List of recent orders           |
| GET    | `/reports/sales`                   | Get sales reports                | timeFrame parameter              | Sales report data               |

**Example Request - Get Admin Dashboard Overview:**
```
GET /api/admin/dashboard/overview
```

**Example Response:**
```json
{
  "success": true,
  "message": "Dashboard overview fetched successfully",
  "data": {
    "activeUsers": 12540,
    "newUsersToday": 125,
    "activeRestaurants": 743,
    "pendingRestaurantVerifications": 15,
    "ordersToday": 3254,
    "totalSalesToday": 65432.50,
    "activeDeliveries": 342,
    "systemStatus": {
      "services": {
        "order": "HEALTHY",
        "payment": "HEALTHY",
        "delivery": "HEALTHY",
        "notification": "DEGRADED"
      },
      "serverLoad": 65,
      "averageResponseTime": 120 // ms
    }
  }
}
```

---

## 13. AI Service

AI service handles personalized recommendations and AI-driven features.

### Base URL: `/api/ai`

| Method | Endpoint                           | Description                     | Request Body                     | Response                       |
|--------|-----------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/recommendations/{userId}`        | Get personalized recommendations | -                                 | List of recommendations         |
| GET    | `/recommendations/similar/{itemId}`| Get similar items                | -                                 | List of similar items           |
| POST   | `/analyze/review`                  | Analyze review sentiment         | Review text                       | Sentiment analysis results      |
| GET    | `/predict/order-time/{userId}`     | Predict user order time          | -                                 | Predicted order time            |
| POST   | `/generate/meal-plan`              | Generate meal plan               | MealPlanRequest object            | Generated meal plan             |

**Example Request - Get Personalized Recommendations:**
```
GET /api/ai/recommendations/123
```

**Example Response:**
```json
{
  "success": true,
  "message": "Recommendations fetched successfully",
  "data": {
    "userId": 123,
    "recommendedRestaurants": [
      {
        "id": 458,
        "name": "Green Garden",
        "cuisineType": ["VEGETARIAN", "HEALTHY"],
        "rating": 4.7,
        "matchScore": 0.92,
        "reason": "Based on your preference for healthy options"
      },
      {
        "id": 459,
        "name": "Spice Route",
        "cuisineType": ["INDIAN"],
        "rating": 4.5,
        "matchScore": 0.85,
        "reason": "Similar to restaurants you've ordered from"
      }
    ],
    "recommendedDishes": [
      {
        "id": 791,
        "restaurantId": 458,
        "name": "Buddha Bowl",
        "price": 13.99,
        "matchScore": 0.94,
        "reason": "Based on your dietary preferences"
      },
      {
        "id": 792,
        "restaurantId": 459,
        "name": "Butter Chicken",
        "price": 16.99,
        "matchScore": 0.88,
        "reason": "Popular with customers who ordered dishes you like"
      }
    ]
  }
}
```

---

## 14. Promotion Service

Promotion service manages promotional offers, coupons, and campaigns.

### Base URL: `/api/promotions`

| Method | Endpoint                           | Description                     | Request Body                     | Response                       |
|--------|-----------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/active`                          | Get active promotions            | -                                 | List of active promotions       |
| GET    | `/{userId}/available`              | Get available user promotions    | -                                 | List of available promotions    |
| POST   | `/validate`                        | Validate promotion code          | PromotionValidateRequest object   | Validation results              |
| POST   | `/apply`                           | Apply promotion to order         | PromotionApplyRequest object      | Updated order with promotion    |
| GET    | `/restaurants/{restaurantId}`      | Get restaurant promotions        | -                                 | List of restaurant promotions   |

**Example Request - Validate Promotion Code:**
```json
POST /api/promotions/validate
{
  "code": "WELCOME25",
  "userId": 123,
  "orderId": 55667,
  "orderAmount": 31.05
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Promotion code is valid",
  "data": {
    "promotionId": "promo-123456",
    "code": "WELCOME25",
    "valid": true,
    "description": "25% off your first order",
    "discountType": "PERCENTAGE",
    "discountValue": 25,
    "discountAmount": 7.76,
    "minimumOrderAmount": 20,
    "expiresAt": "2025-12-31T23:59:59Z",
    "applicableItems": [],
    "restrictedItems": []
  }
}
```

---

## 15. Tracking Service

Tracking service handles real-time order and delivery tracking.

### Base URL: `/api/tracking`

| Method | Endpoint                           | Description                     | Request Body                     | Response                       |
|--------|-----------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/orders/{orderId}`                | Get order tracking details       | -                                 | Order tracking details          |
| GET    | `/orders/{orderId}/live`           | Get live order tracking          | -                                 | Live tracking data              |
| GET    | `/drivers/{driverId}`              | Get driver tracking details      | -                                 | Driver tracking details         |
| POST   | `/drivers/{driverId}/location`     | Update driver location           | Location object                   | Updated location confirmation   |
| GET    | `/users/{userId}/active-orders`    | Get user's active order tracking | -                                 | Active order tracking details   |

**Example Request - Get Live Order Tracking:**
```
GET /api/tracking/orders/55667/live
```

**Example Response:**
```json
{
  "success": true,
  "message": "Live tracking data fetched successfully",
  "data": {
    "orderId": 55667,
    "status": "IN_TRANSIT",
    "estimatedDeliveryTime": "2025-10-13T15:45:00Z",
    "actualDeliveryTime": null,
    "driver": {
      "id": 789,
      "name": "Mike Johnson",
      "phone": "+1234567890",
      "photo": "https://driver-photos.example.com/mike.jpg",
      "vehicle": "Honda Civic",
      "licensePlate": "ABC123"
    },
    "currentLocation": {
      "latitude": 40.7180,
      "longitude": -74.0120,
      "updatedAt": "2025-10-13T15:35:00Z"
    },
    "destinationLocation": {
      "latitude": 40.7128,
      "longitude": -74.0060
    },
    "route": {
      "distance": "0.5 miles",
      "timeRemaining": "10 minutes",
      "polyline": "enc:abcdefghijklmnop..."
    },
    "deliverySteps": [
      {
        "status": "PICKED_UP",
        "timestamp": "2025-10-13T15:25:00Z"
      },
      {
        "status": "IN_TRANSIT",
        "timestamp": "2025-10-13T15:26:00Z"
      }
    ]
  }
}
```

---

## 16. Social Service

Social service handles social features like sharing, following, and activity feeds.

### Base URL: `/api/social`

| Method | Endpoint                           | Description                     | Request Body                     | Response                       |
|--------|-----------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/feed/{userId}`                   | Get user's social feed           | pagination parameters            | User social feed               |
| POST   | `/share`                           | Share content                    | ShareRequest object               | Share confirmation             |
| POST   | `/follow/user/{followId}`          | Follow a user                    | userId parameter                  | Follow confirmation            |
| GET    | `/followers/{userId}`              | Get user's followers             | -                                 | List of followers              |
| GET    | `/following/{userId}`              | Get users followed by user       | -                                 | List of followed users         |
| POST   | `/posts`                           | Create a food post               | PostRequest object                | Created post details           |

**Example Request - Create a Food Post:**
```json
POST /api/social/posts
{
  "userId": 123,
  "restaurantId": 456,
  "itemIds": [789],
  "content": "This Margherita Pizza was amazing! Highly recommend!",
  "rating": 5,
  "images": ["https://user-uploads.example.com/pizza1.jpg"],
  "tags": ["pizza", "italian", "foodie"]
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Post created successfully",
  "data": {
    "id": "post-123456",
    "userId": 123,
    "username": "john_doe",
    "restaurantId": 456,
    "restaurantName": "Bella Italia",
    "items": [
      {
        "id": 789,
        "name": "Margherita Pizza",
        "price": 12.99
      }
    ],
    "content": "This Margherita Pizza was amazing! Highly recommend!",
    "rating": 5,
    "images": ["https://user-uploads.example.com/pizza1.jpg"],
    "tags": ["pizza", "italian", "foodie"],
    "likes": 0,
    "comments": 0,
    "createdAt": "2025-10-13T16:30:00Z"
  }
}
```

---

## 17. Subscription Service

Subscription service manages user subscriptions for premium delivery services.

### Base URL: `/api/subscriptions`

| Method | Endpoint                           | Description                     | Request Body                     | Response                       |
|--------|-----------------------------------|----------------------------------|----------------------------------|--------------------------------|
| GET    | `/plans`                           | Get available subscription plans | -                                 | List of subscription plans      |
| GET    | `/{userId}`                        | Get user subscription            | -                                 | User subscription details       |
| POST   | `/{userId}/subscribe`              | Subscribe to a plan              | SubscriptionRequest object        | Subscription confirmation       |
| PUT    | `/{userId}/cancel`                 | Cancel subscription              | -                                 | Cancellation confirmation       |
| GET    | `/{userId}/benefits`               | Get subscription benefits        | -                                 | List of benefits                |
| GET    | `/analytics/user-count`            | Get subscription analytics       | Admin access required             | Subscription analytics data     |

**Example Request - Subscribe to a Plan:**
```json
POST /api/subscriptions/123/subscribe
{
  "planId": "premium-monthly",
  "paymentMethodId": "card_123456789",
  "autoRenew": true
}
```

**Example Response:**
```json
{
  "success": true,
  "message": "Subscription successful",
  "data": {
    "subscriptionId": "sub-123456",
    "userId": 123,
    "planId": "premium-monthly",
    "planName": "FoodApp Premium Monthly",
    "status": "ACTIVE",
    "benefits": [
      "Free delivery on all orders",
      "Priority customer support",
      "Exclusive discounts"
    ],
    "startDate": "2025-10-13T17:00:00Z",
    "endDate": "2025-11-13T17:00:00Z",
    "autoRenew": true,
    "nextBillingDate": "2025-11-13T17:00:00Z",
    "price": 9.99,
    "currency": "USD"
  }
}
```