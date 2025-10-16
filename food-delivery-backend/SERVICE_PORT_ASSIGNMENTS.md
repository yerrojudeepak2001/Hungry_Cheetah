# Food Delivery Backend - Service Port Assignments

This document lists all microservices and their assigned unique port numbers to avoid conflicts.

## Service Port Mapping

| Service Name | Port | Description |
|-------------|------|-------------|
| **api-gateway** | 8080 | Main API Gateway - Entry point for all client requests |
| **order-service** | 8081 | Order management and processing |
| **auth-service** | 8082 | Authentication and authorization |
| **user-service** | 8083 | User management and profiles |
| **payment-service** | 8084 | Payment processing and transactions |
| **delivery-service** | 8085 | Delivery tracking and management |
| **restaurant-service** | 8087 | Restaurant and menu management |
| **kitchen-service** | 8090 | Kitchen operations and order preparation |
| **quality-service** | 8091 | Quality assurance and ratings |
| **inventory-service** | 8092 | Inventory management |
| **recommendation-service** | 8093 | AI-powered recommendations |
| **driver-service** | 8094 | Driver management and tracking |
| **ai-service** | 8095 | AI/ML services |
| **analytics-service** | 8096 | Analytics and reporting |
| **subscription-service** | 8097 | Subscription management |
| **social-service** | 8098 | Social features and sharing |
| **admin-service** | 8099 | Administrative functions |
| **tracking-service** | 8100 | Order and delivery tracking |
| **loyalty-service** | 8101 | Loyalty programs and rewards |
| **pricing-service** | 8102 | Dynamic pricing algorithms |
| **promotion-service** | 8103 | Promotions and discount management |
| **cart-service** | 8104 | Shopping cart management |
| **search-service** | 8105 | Search and filtering |
| **notification-service** | 8106 | Push notifications and messaging |

## Infrastructure Services

| Service Name | Port | Description |
|-------------|------|-------------|
| **service-registry** | 8761 | Eureka Service Discovery |
| **config-server** | 8888 | Spring Cloud Config Server |

## Port Range Summary

- **Application Services**: 8080-8106 (27 services)
- **Infrastructure Services**: 8761, 8888
- **Available Ports**: 8086, 8088, 8089, 8107-8760, 8762-8887, 8889+

## Conflict Resolution

- ✅ **Fixed**: restaurant-service moved from 8083 to 8087 (was conflicting with user-service)

## Notes

1. All services are configured to run on localhost by default
2. Service discovery is handled through Eureka (port 8761)
3. Configuration is centralized through Config Server (port 8888)
4. API Gateway (8080) serves as the main entry point for external clients
5. Each service can be accessed directly via its assigned port for testing/debugging

## How to Start Services

```powershell
# Start infrastructure services first
cd config-server; mvn spring-boot:run &
cd service-registry; mvn spring-boot:run &

# Start core services
cd api-gateway; mvn spring-boot:run &
cd auth-service; mvn spring-boot:run &
cd user-service; mvn spring-boot:run &

# Start other services as needed
cd [service-name]; mvn spring-boot:run &
```

---
*Last updated: October 16, 2025*