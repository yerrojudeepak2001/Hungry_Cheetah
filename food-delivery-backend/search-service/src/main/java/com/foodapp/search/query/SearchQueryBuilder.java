package com.foodapp.search.query;

import lombok.Builder;
import lombok.Data;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.Query;
import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class SearchQueryBuilder {
    private String searchTerm;
    private List<FilterCriteria> filters;
    private List<SortCriteria> sorts;
    private Integer page;
    private Integer size;
    private List<String> fields;

    public Query buildQuery() {
        NativeSearchQueryBuilder queryBuilder = new NativeSearchQueryBuilder();
        BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

        // Add search term
        if (searchTerm != null && !searchTerm.isEmpty()) {
            boolQuery.must(QueryBuilders.multiMatchQuery(searchTerm)
                .field("name", 1.5f)
                .field("description")
                .field("cuisine")
                .field("tags"));
        }

        // Add filters
        if (filters != null) {
            filters.forEach(filter -> {
                switch (filter.getOperation()) {
                    case EQUALS:
                        boolQuery.filter(QueryBuilders.termQuery(filter.getField(), filter.getValue()));
                        break;
                    case RANGE:
                        boolQuery.filter(QueryBuilders.rangeQuery(filter.getField())
                            .from(filter.getFrom())
                            .to(filter.getTo()));
                        break;
                    case IN:
                        boolQuery.filter(QueryBuilders.termsQuery(filter.getField(), filter.getValues()));
                        break;
                }
            });
        }

        queryBuilder.withQuery(boolQuery);

        // Add sorts
        if (sorts != null) {
            sorts.forEach(sort -> 
                queryBuilder.withSort(org.springframework.data.domain.Sort.by(
                    sort.getDirection(), sort.getField())));
        }

        // Add pagination
        if (page != null && size != null) {
            queryBuilder.withPageable(org.springframework.data.domain.PageRequest.of(page, size));
        }

        return queryBuilder.build();
    }

    @Data
    @Builder
    public static class FilterCriteria {
        private String field;
        private FilterOperation operation;
        private Object value;
        private Object from;
        private Object to;
        private List<Object> values;

        public enum FilterOperation {
            EQUALS, RANGE, IN
        }
    }

    @Data
    @Builder
    public static class SortCriteria {
        private String field;
        private org.springframework.data.domain.Sort.Direction direction;
    }
}