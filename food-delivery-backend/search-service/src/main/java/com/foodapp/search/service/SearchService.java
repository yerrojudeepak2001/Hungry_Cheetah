package com.foodapp.search.service;

import com.foodapp.search.model.RestaurantSearchDocument;
import com.foodapp.search.repository.RestaurantSearchRepository;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SearchService {
    private final RestaurantSearchRepository searchRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final RecommendationService recommendationService;

    public SearchService(RestaurantSearchRepository searchRepository,
                        ElasticsearchOperations elasticsearchOperations,
                        RecommendationService recommendationService) {
        this.searchRepository = searchRepository;
        this.elasticsearchOperations = elasticsearchOperations;
        this.recommendationService = recommendationService;
    }

    public List<RestaurantSearchDocument> searchRestaurants(String query, 
                                                          Double latitude, 
                                                          Double longitude, 
                                                          List<String> cuisines,
                                                          List<String> features,
                                                          Double minRating,
                                                          Double maxPrice,
                                                          Integer radius) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // Text search
        if (query != null && !query.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(query)
                .field("name", 2.0f)
                .field("description")
                .field("popularItems.name")
                .field("tags"));
        }

        // Location filter
        if (latitude != null && longitude != null && radius != null) {
            boolQuery.filter(QueryBuilders.geoDistanceQuery("location")
                .point(latitude, longitude)
                .distance(radius, DistanceUnit.KILOMETERS));
        }

        // Cuisine filter
        if (cuisines != null && !cuisines.isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("cuisines", cuisines));
        }

        // Features filter
        if (features != null && !features.isEmpty()) {
            boolQuery.filter(QueryBuilders.termsQuery("features", features));
        }

        // Rating filter
        if (minRating != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("rating").gte(minRating));
        }

        // Price range filter
        if (maxPrice != null) {
            boolQuery.filter(QueryBuilders.rangeQuery("priceRange").lte(maxPrice));
        }

        // Only open restaurants
        boolQuery.filter(QueryBuilders.termQuery("isOpen", true));

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
            .withQuery(boolQuery)
            .withSort(org.springframework.data.elasticsearch.core.query.Sort.by("rating").descending());

        SearchHits<RestaurantSearchDocument> searchHits = 
            elasticsearchOperations.search(searchQueryBuilder.build(), RestaurantSearchDocument.class);

        return searchHits.stream()
            .map(hit -> hit.getContent())
            .collect(Collectors.toList());
    }

    public List<RestaurantSearchDocument> searchByLocation(Double latitude, 
                                                         Double longitude, 
                                                         Integer radius) {
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
            .filter(QueryBuilders.geoDistanceQuery("location")
                .point(latitude, longitude)
                .distance(radius, DistanceUnit.KILOMETERS))
            .filter(QueryBuilders.termQuery("isOpen", true));

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
            .withQuery(boolQuery)
            .withSort(org.springframework.data.elasticsearch.core.query.Sort.by("rating").descending());

        SearchHits<RestaurantSearchDocument> searchHits = 
            elasticsearchOperations.search(searchQueryBuilder.build(), RestaurantSearchDocument.class);

        return searchHits.stream()
            .map(hit -> hit.getContent())
            .collect(Collectors.toList());
    }

    public List<RestaurantSearchDocument> getRecommendedRestaurants(Long userId, 
                                                                   Double latitude, 
                                                                   Double longitude) {
        // Get user preferences and order history from recommendation service
        List<String> preferredCuisines = recommendationService.getUserPreferredCuisines(userId);
        Double preferredPriceRange = recommendationService.getUserPreferredPriceRange(userId);

        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery()
            .should(QueryBuilders.termsQuery("cuisines", preferredCuisines))
            .should(QueryBuilders.rangeQuery("priceRange").lte(preferredPriceRange))
            .filter(QueryBuilders.termQuery("isOpen", true));

        if (latitude != null && longitude != null) {
            boolQuery.filter(QueryBuilders.geoDistanceQuery("location")
                .point(latitude, longitude)
                .distance(5, DistanceUnit.KILOMETERS));
        }

        NativeSearchQueryBuilder searchQueryBuilder = new NativeSearchQueryBuilder()
            .withQuery(boolQuery)
            .withSort(org.springframework.data.elasticsearch.core.query.Sort.by("rating").descending());

        SearchHits<RestaurantSearchDocument> searchHits = 
            elasticsearchOperations.search(searchQueryBuilder.build(), RestaurantSearchDocument.class);

        return searchHits.stream()
            .map(hit -> hit.getContent())
            .collect(Collectors.toList());
    }

    public void indexRestaurant(RestaurantSearchDocument restaurant) {
        searchRepository.save(restaurant);
    }

    public void deleteRestaurantIndex(String restaurantId) {
        searchRepository.deleteById(restaurantId);
    }
}