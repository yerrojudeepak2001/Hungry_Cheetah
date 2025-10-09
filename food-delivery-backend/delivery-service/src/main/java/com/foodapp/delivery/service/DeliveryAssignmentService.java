package com.foodapp.delivery.service;

import com.foodapp.delivery.model.DeliveryAgent;
import com.foodapp.delivery.model.DeliveryAssignment;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.NearQuery;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class DeliveryAssignmentService {
    private final MongoTemplate mongoTemplate;
    private final DeliveryAgentRepository agentRepository;
    private final DeliveryAssignmentRepository assignmentRepository;

    public DeliveryAssignmentService(
            MongoTemplate mongoTemplate,
            DeliveryAgentRepository agentRepository,
            DeliveryAssignmentRepository assignmentRepository) {
        this.mongoTemplate = mongoTemplate;
        this.agentRepository = agentRepository;
        this.assignmentRepository = assignmentRepository;
    }

    public DeliveryAssignment assignOptimalDeliveryAgent(Order order, Restaurant restaurant) {
        // Get restaurant location
        Point restaurantLocation = new Point(
            restaurant.getLocation().getCoordinates()[0],
            restaurant.getLocation().getCoordinates()[1]
        );

        // Get delivery location
        Point deliveryLocation = getDeliveryLocation(order.getDeliveryAddress());

        // Find nearby available agents
        List<DeliveryAgent> nearbyAgents = findNearbyAvailableAgents(restaurantLocation, 5000); // 5km radius

        if (nearbyAgents.isEmpty()) {
            throw new RuntimeException("No available delivery agents nearby");
        }

        // Score each agent based on multiple factors
        DeliveryAgent bestAgent = findBestAgent(nearbyAgents, restaurantLocation, deliveryLocation, order);

        // Create assignment
        DeliveryAssignment assignment = new DeliveryAssignment();
        assignment.setOrderId(order.getId());
        assignment.setAgentId(bestAgent.getId());
        assignment.setRestaurantLocation(restaurantLocation);
        assignment.setDeliveryLocation(deliveryLocation);
        assignment.setStatus("ASSIGNED");
        assignment.setEstimatedPickupTime(calculateEstimatedPickupTime(bestAgent, restaurantLocation));
        assignment.setEstimatedDeliveryTime(calculateEstimatedDeliveryTime(
            bestAgent, restaurantLocation, deliveryLocation, order.getPreparationTime()));

        // Update agent status
        bestAgent.setStatus("ASSIGNED");
        bestAgent.setCurrentAssignment(assignment.getId());
        agentRepository.save(bestAgent);

        return assignmentRepository.save(assignment);
    }

    private List<DeliveryAgent> findNearbyAvailableAgents(Point location, double maxDistance) {
        NearQuery nearQuery = NearQuery.near(location)
            .maxDistance(new Distance(maxDistance, Metrics.METERS))
            .query(Query.query(Criteria.where("status").is("AVAILABLE")));

        return mongoTemplate.geoNear(nearQuery, DeliveryAgent.class);
    }

    private DeliveryAgent findBestAgent(
            List<DeliveryAgent> agents,
            Point restaurantLocation,
            Point deliveryLocation,
            Order order) {
        
        return agents.stream()
            .map(agent -> {
                double score = calculateAgentScore(
                    agent, restaurantLocation, deliveryLocation, order);
                return new AbstractMap.SimpleEntry<>(agent, score);
            })
            .max(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .orElseThrow(() -> new RuntimeException("Could not find suitable agent"));
    }

    private double calculateAgentScore(
            DeliveryAgent agent,
            Point restaurantLocation,
            Point deliveryLocation,
            Order order) {
        
        double score = 0.0;

        // Distance to restaurant (closer is better)
        double distanceToRestaurant = calculateDistance(
            agent.getCurrentLocation(), restaurantLocation);
        score += (1.0 / distanceToRestaurant) * 40;

        // Agent rating (higher is better)
        score += agent.getRating() * 20;

        // Agent experience (more deliveries is better)
        score += Math.min(agent.getTotalDeliveries() / 1000.0, 1.0) * 15;

        // Order value consideration (higher value orders to more experienced agents)
        if (order.getTotalAmount().compareTo(BigDecimal.valueOf(50)) > 0) {
            score += agent.getTotalDeliveries() > 100 ? 10 : 0;
        }

        // Preferred areas bonus
        if (agent.getPreferredAreas().contains(getAreaCode(deliveryLocation))) {
            score += 10;
        }

        // Vehicle type consideration
        score += getVehicleTypeScore(agent.getVehicleType(), 
            distanceToRestaurant + calculateDistance(restaurantLocation, deliveryLocation));

        return score;
    }

    private double getVehicleTypeScore(String vehicleType, double totalDistance) {
        // Score vehicle types based on delivery distance
        if (totalDistance <= 2000) { // 2km
            return vehicleType.equals("BICYCLE") ? 10 : 5;
        } else if (totalDistance <= 5000) { // 5km
            return vehicleType.equals("MOTORCYCLE") ? 10 : 5;
        } else {
            return vehicleType.equals("CAR") ? 10 : 5;
        }
    }

    private LocalDateTime calculateEstimatedPickupTime(DeliveryAgent agent, Point restaurantLocation) {
        double distance = calculateDistance(agent.getCurrentLocation(), restaurantLocation);
        int estimatedMinutes = (int) (distance / agent.getAverageSpeed());
        return LocalDateTime.now().plusMinutes(estimatedMinutes);
    }

    private LocalDateTime calculateEstimatedDeliveryTime(
            DeliveryAgent agent,
            Point restaurantLocation,
            Point deliveryLocation,
            int preparationTime) {
        
        double totalDistance = calculateDistance(agent.getCurrentLocation(), restaurantLocation) +
                             calculateDistance(restaurantLocation, deliveryLocation);
        
        int travelMinutes = (int) (totalDistance / agent.getAverageSpeed());
        return LocalDateTime.now()
            .plusMinutes(preparationTime)
            .plusMinutes(travelMinutes)
            .plusMinutes(5); // Buffer time
    }

    private double calculateDistance(Point p1, Point p2) {
        // Haversine formula implementation
        double R = 6371e3; // Earth's radius in meters
        double φ1 = Math.toRadians(p1.getY());
        double φ2 = Math.toRadians(p2.getY());
        double Δφ = Math.toRadians(p2.getY() - p1.getY());
        double Δλ = Math.toRadians(p2.getX() - p1.getX());

        double a = Math.sin(Δφ/2) * Math.sin(Δφ/2) +
                Math.cos(φ1) * Math.cos(φ2) *
                Math.sin(Δλ/2) * Math.sin(Δλ/2);
        
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));

        return R * c;
    }
}