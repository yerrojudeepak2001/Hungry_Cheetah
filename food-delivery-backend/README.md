# Food Delivery App Backend Microservices

This is a microservices-based backend system for a Food Delivery Application built using Spring Boot 3.3+, Spring Cloud, and various other modern technologies.

## Features

- Real-time order tracking
- AI-powered meal recommendations
- Weather-based suggestions
- Voice-based ordering
- JMS-driven asynchronous communication

## Technology Stack

- Backend Framework: Spring Boot 3.3+
- Service Discovery: Spring Cloud Eureka
- API Gateway: Spring Cloud Gateway
- Config Management: Spring Cloud Config
- Databases: MySQL, MongoDB
- Messaging: JMS (ActiveMQ Artemis)
- AI Integrations: Gemini API, OpenWeather
- Voice Ordering: Gemini NLP + Twilio
- Deployment: Docker, Docker Compose
- Monitoring: Prometheus + Grafana

## Services

1. Service Registry (Eureka Server) - Port: 8761
2. Config Server - Port: 8888
3. API Gateway - Port: 8080
4. Auth Service
5. User Service
6. Restaurant Service
7. Order Service
8. Payment Service
9. Delivery Service
10. Notification Service
11. AI Service
12. Analytics Service
13. Admin Service
14. Common Library

## Prerequisites

- Java 17+
- Maven 3.8+
- Docker and Docker Compose
- MySQL 8.0
- MongoDB
- ActiveMQ Artemis

## Setup Instructions

1. Clone the repository:
```bash
git clone <repository-url>
cd food-delivery-backend
```

2. Build the project:
```bash
mvn clean install
```

3. Start the infrastructure services using Docker Compose:
```bash
docker-compose up -d
```

4. The services can be accessed at:
- Eureka Dashboard: http://localhost:8761
- Gateway API: http://localhost:8080
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

## Running the Services

Start the services in the following order:

1. Service Registry
2. Config Server
3. API Gateway
4. Other services

Each service can be started using:
```bash
java -jar <service-name>/target/<service-name>-1.0.0.jar
```

## API Documentation

API documentation is available at:
- http://localhost:8080/swagger-ui.html (when running locally)

## Monitoring

The application can be monitored using:
- Prometheus: http://localhost:9090
- Grafana: http://localhost:3000

## Testing

To run the tests:
```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create your feature branch
3. Commit your changes
4. Push to the branch
5. Create a new Pull Request