# Restaurant Service - Hybrid Database Architecture

## Overview

The Restaurant Service uses a **hybrid database approach**, leveraging both MySQL and MongoDB to optimize for different data patterns and use cases.

## Database Strategy

### MySQL (Relational Database)
Used for **structured, transactional data** with strong consistency requirements:

- **Restaurant** - Core restaurant information
- **MenuItem** - Menu items with fixed schema
- **SpecialMenu** - Time-bound special menu offerings
- **CateringService** - Catering service information

#### Why MySQL?
- Strong ACID properties for transactional data
- Complex joins between restaurants and menu items
- Fixed schema with well-defined relationships
- SQL queries for reporting and analytics

### MongoDB (NoSQL Database)
Used for **flexible, unstructured data** with evolving schemas:

- **ReviewDocument** - Customer reviews with flexible attributes
  - Detailed ratings (food, service, ambiance, value)
  - Photos and videos
  - Restaurant responses
  - Engagement metrics (helpful votes)
  - Tags and moderation status

- **ARMenuDocument** - AR/VR menu content
  - 3D model metadata
  - Platform-specific configurations
  - Analytics and device usage
  - Flexible AR properties

- **VirtualTourDocument** - Virtual tour data
  - Tour stops with dynamic properties
  - Interactive hotspots
  - Audio guides
  - Accessibility features

#### Why MongoDB?
- Flexible schema for evolving features
- Efficient storage of nested/embedded documents
- Better performance for read-heavy operations
- Natural fit for hierarchical data (reviews with responses, tours with stops)

## Configuration

### application.yml

```yaml
spring:
  # MySQL Configuration
  datasource:
    url: jdbc:mysql://100.122.144.43:3306/food_app
    username: teamuser
    password: password123
  jpa:
    hibernate:
      ddl-auto: update
      
  # MongoDB Configuration
  data:
    mongodb:
      uri: mongodb://localhost:27017/restaurant_service
```

### DatabaseConfiguration.java

Enables both JPA and MongoDB repositories in separate packages:
- JPA Repositories: `com.foodapp.restaurant.repository`
- MongoDB Repositories: `com.foodapp.restaurant.repository.mongo`

## Data Flow Examples

### Adding a Review

1. Verify restaurant exists in **MySQL**
2. Create review document in **MongoDB** with flexible schema
3. Return response with combined data

### Viewing Restaurant with Reviews

1. Fetch restaurant from **MySQL**
2. Fetch reviews from **MongoDB**
3. Calculate average rating from MongoDB data
4. Combine and return

### Uploading AR Menu

1. Verify restaurant exists in **MySQL**
2. Store AR menu with metadata in **MongoDB**
3. Track analytics in MongoDB (view counts, device usage)

## Benefits of Hybrid Approach

1. **Performance Optimization**
   - MySQL handles complex joins and transactions
   - MongoDB handles high-volume reads and flexible data

2. **Schema Flexibility**
   - Add new review attributes without database migrations
   - Evolve AR menu features without schema changes

3. **Scalability**
   - Scale MongoDB horizontally for review/media data
   - Keep relational data in MySQL with vertical scaling

4. **Data Integrity**
   - Maintain referential integrity for core business data
   - Allow flexible documents for user-generated content

## MongoDB Collections

### reviews
```javascript
{
  "_id": "ObjectId",
  "restaurantId": 123,
  "userId": 456,
  "userName": "John Doe",
  "rating": 5,
  "comment": "Great food!",
  "detailedRatings": {
    "food": 5,
    "service": 4,
    "ambiance": 5
  },
  "photoUrls": ["url1", "url2"],
  "helpfulCount": 10,
  "restaurantResponse": {
    "responseText": "Thank you!",
    "respondedAt": "2025-10-14T10:00:00"
  },
  "status": "APPROVED"
}
```

### ar_menus
```javascript
{
  "_id": "ObjectId",
  "restaurantId": 123,
  "modelUrl": "https://...",
  "arMetadata": {
    "supportsAndroid": true,
    "supportsIOS": true
  },
  "viewCount": 1500,
  "configuration": {
    "autoRotate": true,
    "allowScaling": true
  }
}
```

### virtual_tours
```javascript
{
  "_id": "ObjectId",
  "restaurantId": 123,
  "tourType": "360_PANORAMA",
  "tourStops": [
    {
      "name": "Entrance",
      "panoramaUrl": "https://..."
    }
  ],
  "hotSpots": [...],
  "viewCount": 2000
}
```

## Repository Usage

### JPA Repository (MySQL)
```java
@Autowired
private RestaurantRepository restaurantRepository;

Restaurant restaurant = restaurantRepository.findById(id).orElseThrow();
```

### MongoDB Repository
```java
@Autowired
private ReviewMongoRepository reviewMongoRepository;

List<ReviewDocument> reviews = reviewMongoRepository.findByRestaurantId(restaurantId);
```

## Running the Service

1. **Start MySQL**
   ```bash
   # Already running at 100.122.144.43:3306
   ```

2. **Start MongoDB**
   ```bash
   # Using Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   
   # Or install MongoDB locally
   # Download from: https://www.mongodb.com/try/download/community
   ```

3. **Run the Service**
   ```bash
   mvn spring-boot:run
   ```

## Testing

### MySQL Data
```bash
# View restaurants
mysql -h 100.122.144.43 -u teamuser -p food_app
SELECT * FROM restaurants;
```

### MongoDB Data
```bash
# View reviews
mongo localhost:27017/restaurant_service
db.reviews.find().pretty()
```

## Future Enhancements

1. **Read Replicas**: Add MongoDB read replicas for improved read performance
2. **Caching**: Implement Redis caching for frequently accessed data
3. **Search**: Add Elasticsearch for full-text search across reviews
4. **Analytics**: Stream MongoDB data to analytics platform