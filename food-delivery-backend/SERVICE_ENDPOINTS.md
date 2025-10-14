# Food Delivery Microservices - API Endpoints

## Running Services Overview

| Service | Port | Status | Eureka Registration |
|---------|------|--------|-------------------|
| **Service Registry (Eureka)** | 8761 | ✅ Running | Self-registered |
| **Config Server** | 8888 | ✅ Running | Registered |
| **Auth Service** | 8082 | ✅ Running | AUTH-SERVICE |
| **User Service** | 8083 | ✅ Running | USER-SERVICE |
| **Admin Service** | 8099 | ✅ Running | ADMIN-SERVICE |

---

## 1. Service Registry (Eureka Server) - Port 8761

### Dashboard
- **URL**: `http://localhost:8761`
- **Description**: Eureka dashboard to view all registered services
- **Access**: Open in browser

### Endpoints
- `GET /eureka/apps` - List all registered applications
- `GET /eureka/apps/{appName}` - Get specific application info

---

## 2. Config Server - Port 8888

### Base URL
`http://localhost:8888`

### Authentication
- **Username**: `configuser`
- **Password**: `configpassword`

### Endpoints
- `GET /{application}/{profile}` - Get configuration for application and profile
- `GET /{application}/{profile}/{label}` - Get configuration from specific git branch
- `GET /{application}-{profile}.yml` - Get configuration as YAML
- `GET /{application}-{profile}.properties` - Get configuration as properties

### Examples
```bash
# Get auth-service configuration
curl -u configuser:configpassword http://localhost:8888/auth-service/default

# Get user-service dev profile configuration
curl -u configuser:configpassword http://localhost:8888/user-service/dev
```

---

## 3. Auth Service - Port 8082

### Base URL
`http://localhost:8082/api/auth`

### Endpoints

#### Authentication
| Method | Endpoint | Description | Request Body |
|--------|----------|-------------|--------------|
| POST | `/register` | Register new user | `{ "username": "string", "password": "string", "email": "string" }` |
| POST | `/login` | User login | `{ "username": "string", "password": "string" }` |
| POST | `/logout` | User logout | `{ "token": "string" }` |
| POST | `/refresh-token` | Refresh JWT token | `{ "refreshToken": "string" }` |
| POST | `/validate-token` | Validate JWT token | `{ "token": "string" }` |

#### User Information
| Method | Endpoint | Description | Headers |
|--------|----------|-------------|---------|
| GET | `/userinfo` | Get current user info | `Authorization: Bearer {token}` |
| GET | `/user/{username}` | Get user by username | `Authorization: Bearer {token}` |

### Sample Requests

#### Register
```bash
curl -X POST http://localhost:8082/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123!",
    "email": "john@example.com",
    "firstName": "John",
    "lastName": "Doe"
  }'
```

#### Login
```bash
curl -X POST http://localhost:8082/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "john_doe",
    "password": "SecurePass123!"
  }'
```

#### Validate Token
```bash
curl -X POST http://localhost:8082/api/auth/validate-token \
  -H "Content-Type: application/json" \
  -d '{
    "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
  }'
```

---

## 4. User Service - Port 8083

### Base URL
`http://localhost:8083/api/users`

### Endpoints

#### User Management
| Method | Endpoint | Description | Authorization |
|--------|----------|-------------|---------------|
| POST | `/register` | Register new user | Public |
| GET | `/{userId}` | Get user by ID | Required |
| PUT | `/{userId}` | Update user profile | Required |

#### User Preferences
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/{userId}/preferences` | Update user preferences |
| GET | `/{userId}/preferences` | Get user preferences |
| POST | `/{userId}/dietary-restrictions` | Add dietary restrictions |
| GET | `/{userId}/dietary-restrictions` | Get dietary restrictions |

#### Address Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/{userId}/addresses` | Add new address |
| GET | `/{userId}/addresses` | Get all addresses |
| PUT | `/{userId}/addresses/{addressId}` | Update address |
| DELETE | `/{userId}/addresses/{addressId}` | Delete address |

#### Favorites & History
| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/{userId}/order-history` | Get order history |
| GET | `/{userId}/favorite-restaurants` | Get favorite restaurants |
| POST | `/{userId}/favorite-restaurants/{restaurantId}` | Add favorite restaurant |
| DELETE | `/{userId}/favorite-restaurants/{restaurantId}` | Remove favorite restaurant |

### Sample Requests

#### Get User Profile
```bash
curl -X GET http://localhost:8083/api/users/1 \
  -H "Authorization: Bearer {token}"
```

#### Update User Profile
```bash
curl -X PUT http://localhost:8083/api/users/1 \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "firstName": "John",
    "lastName": "Doe",
    "email": "john.doe@example.com",
    "phone": "+1234567890"
  }'
```

#### Add Address
```bash
curl -X POST http://localhost:8083/api/users/1/addresses \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "street": "123 Main St",
    "city": "New York",
    "state": "NY",
    "zipCode": "10001",
    "country": "USA",
    "label": "Home"
  }'
```

#### Add Dietary Restrictions
```bash
curl -X POST http://localhost:8083/api/users/1/dietary-restrictions \
  -H "Authorization: Bearer {token}" \
  -H "Content-Type: application/json" \
  -d '{
    "restrictions": ["vegetarian", "gluten-free", "nut-allergy"]
  }'
```

---

## 5. Admin Service - Port 8099

### Base URL
`http://localhost:8099/api/v1/admin`

### Endpoints

#### Dashboard
| Method | Endpoint | Description | Authorization |
|--------|----------|-------------|---------------|
| GET | `/dashboard/stats` | Get dashboard statistics | Admin Required |
| GET | `/system/health` | Get system health status | Admin Required |

#### System Management
| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/system/cache/clear` | Clear system cache |
| GET | `/audit-logs` | Get audit logs |

### Sample Requests

#### Get Dashboard Stats
```bash
curl -X GET http://localhost:8099/api/v1/admin/dashboard/stats \
  -H "Authorization: Bearer {admin-token}"
```

#### Get System Health
```bash
curl -X GET http://localhost:8099/api/v1/admin/system/health \
  -H "Authorization: Bearer {admin-token}"
```

#### Clear Cache
```bash
curl -X POST http://localhost:8099/api/v1/admin/system/cache/clear \
  -H "Authorization: Bearer {admin-token}"
```

---

## Common Response Format

### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": {
    // Response data
  },
  "timestamp": "2025-10-14T11:15:00Z"
}
```

### Error Response
```json
{
  "success": false,
  "message": "Error message",
  "error": {
    "code": "ERROR_CODE",
    "details": "Detailed error information"
  },
  "timestamp": "2025-10-14T11:15:00Z"
}
```

---

## Testing the Services

### Check Service Health
```bash
# Service Registry
curl http://localhost:8761/actuator/health

# Auth Service  
curl http://localhost:8082/actuator/health

# User Service
curl http://localhost:8083/actuator/health

# Admin Service
curl http://localhost:8099/actuator/health
```

### View Eureka Dashboard
Open in browser: `http://localhost:8761`

### Check Registered Services
```bash
curl http://localhost:8761/eureka/apps
```

---

## Authentication Flow

1. **Register User** (Auth Service)
   ```
   POST /api/auth/register
   ```

2. **Login** (Auth Service)
   ```
   POST /api/auth/login
   Response: { "token": "JWT_TOKEN", "refreshToken": "REFRESH_TOKEN" }
   ```

3. **Use Token** in subsequent requests
   ```
   Header: Authorization: Bearer JWT_TOKEN
   ```

4. **Refresh Token** when expired
   ```
   POST /api/auth/refresh-token
   Body: { "refreshToken": "REFRESH_TOKEN" }
   ```

---

## Database Configuration

All services connect to:
- **Host**: `100.122.144.43:3306`
- **Database**: `food_app`
- **Username**: `teamuser`
- **Password**: `password123`

MongoDB (for flexible data):
- **Host**: `localhost:27017`
- **Database**: Service-specific

---

## Security Notes

⚠️ **Development Mode**: All services are running with generated security passwords. 
⚠️ **JWT Secret**: Using default secret key for development only.
⚠️ **Config Server**: Uses basic auth (`configuser:configpassword`)

🔒 **For Production**: 
- Update all passwords and secrets
- Enable HTTPS
- Configure proper authentication
- Use secure key management (Vault, AWS Secrets Manager, etc.)

---

## Service Inter-Communication

Services communicate through:
1. **Eureka Discovery** - Service registration and discovery
2. **Load Balancer** - Client-side load balancing
3. **Feign Clients** - Declarative REST clients
4. **REST APIs** - HTTP/JSON communication

Example Feign Client usage in User Service:
```java
@FeignClient("ORDER-SERVICE")
public interface OrderClient {
    @GetMapping("/api/orders/user/{userId}")
    List<Order> getUserOrders(@PathVariable Long userId);
}
```

---

## Monitoring & Actuator Endpoints

All services expose Spring Boot Actuator endpoints:

- `/actuator/health` - Health check
- `/actuator/info` - Application info
- `/actuator/metrics` - Metrics
- `/actuator/env` - Environment properties

---

## Next Steps

To start additional services:
- **Restaurant Service** (Port 8084) - With MongoDB for reviews, AR menus
- **Order Service** (Port 8081) - Order management
- **Cart Service** (Port 8104) - Shopping cart
- **Payment Service** - Payment processing
- **Delivery Service** - Delivery tracking
- **Notification Service** - Push notifications

---

**Generated**: October 14, 2025
**Status**: All listed services are running and registered with Eureka
