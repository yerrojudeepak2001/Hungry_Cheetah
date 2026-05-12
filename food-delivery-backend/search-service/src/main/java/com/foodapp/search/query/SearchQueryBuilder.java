package com.foodapp.search.query;

import lombok.Builder;
import lombok.Data;
// TODO: Add ElasticSearch imports when dependencies are resolved
// import org.elasticsearch.index.query.BoolQueryBuilder;
// import org.elasticsearch.index.query.QueryBuilders;
// import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
// import org.springframework.data.elasticsearch.core.query.Query;
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

    public Object buildQuery() {
        // TODO: Implement ElasticSearch query building when dependencies are resolved
        return new Object();
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