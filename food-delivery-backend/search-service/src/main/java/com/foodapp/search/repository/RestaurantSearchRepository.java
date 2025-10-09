package com.foodapp.search.repository;

import com.foodapp.search.model.RestaurantSearchDocument;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RestaurantSearchRepository extends ElasticsearchRepository<RestaurantSearchDocument, String> {
    List<RestaurantSearchDocument> findByNameContainingOrDescriptionContaining(String name, String description);
    List<RestaurantSearchDocument> findByCuisinesIn(List<String> cuisines);
    List<RestaurantSearchDocument> findByIsOpenAndRatingGreaterThan(Boolean isOpen, Double minRating);
    List<RestaurantSearchDocument> findByFeaturesIn(List<String> features);
}