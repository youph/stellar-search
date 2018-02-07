package au.com.d2dcrc.ia.search.elastic.mapping;

/**
 * Types used in elasticsearch mappings.
 */
public enum ElasticsearchMappingType {

    /**
     * Elasticsearch field type for full-text values.
     */
    TEXT,

    /**
     * Elasticsearch field type for structured data.
     */
    KEYWORD,

    /**
     * Elasticsearch field type for boolean.
     */
    BOOLEAN,

    /**
     * Elasticsearch field type for short.
     */
    SHORT
}
