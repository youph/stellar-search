package au.com.d2dcrc.ia.search.elastic;

import au.com.d2dcrc.ia.search.elastic.mapping.ElasticsearchMappingBuilder;
import au.com.d2dcrc.ia.search.management.EpgSchema;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexResponse;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

/**
 * Helper for working with elasticsearch indices.
 */
@Component
public class ElasticsearchIndexHelper {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchIndexHelper.class);
    private final ObjectMapper mapper;
    private final ElasticsearchMappingBuilder mappingBuilder;
    private final RestHighLevelClient restHighLevelClient;

    /**
     * Constructor.
     *
     * @param objectMapper the JSON mapper
     * @param mappingBuilder creates elasticsearch mappings
     * @param restHighLevelClient the elasticsearch client
     */
    @Inject
    public ElasticsearchIndexHelper(
        ObjectMapper objectMapper,
        ElasticsearchMappingBuilder mappingBuilder,
        RestHighLevelClient restHighLevelClient
    ) {
        this.mapper = objectMapper;
        this.mappingBuilder = mappingBuilder;
        this.restHighLevelClient = restHighLevelClient;
    }

    /**
     * Create a new elasticsearch index.
     *
     * @param indexName the name of the index
     * @param epgSchema the EPG schema to use for the mapping
     */
    public void createIndex(String indexName, EpgSchema epgSchema) throws ElasticsearchException {
        final String indexMapping = epgSchema == null ? mappingBuilder.createMapping()
            : mappingBuilder.createMapping(epgSchema);

        final HttpEntity entity = new NStringEntity(indexMapping, ContentType.APPLICATION_JSON);

        try {
            restHighLevelClient.getLowLevelClient()
                .performRequest(HttpMethod.PUT.toString(), indexName, Collections.emptyMap(),
                    entity);
            logger.debug("Creating elastic index: " + indexName);
        } catch (IOException e) {
            throw new ElasticsearchException(e);
        }
    }

    /**
     * Delete an elasticsearch index.
     *
     * @param indexName the name of the index to delete
     */
    public void deleteElasticIndex(String indexName) throws ElasticsearchException {
        final DeleteIndexRequest request = new DeleteIndexRequest(indexName);

        try {
            DeleteIndexResponse response = restHighLevelClient
                .indices().delete(request);

            if (response.isAcknowledged()) {
                logger.debug("Deleting elastic index: " + indexName);
            } else {
                logger.warn("Could not delete elastic index: {}", indexName);
            }

        } catch (IOException e) {
            throw new ElasticsearchException(e);
        }
    }

    /**
     * Delete all elasticsearch indices.
     */
    public void deleteAllElasticIndices() {
        final Set<String> indices = getElasticIndices();
        for (String index : indices) {
            deleteElasticIndex(index);
        }
    }

    /**
     * Get all indices from elasticsearch.
     *
     * @return Set of elasticsearch index names
     */
    public Set<String> getElasticIndices() throws ElasticsearchException {
        try {
            final Response response = restHighLevelClient.getLowLevelClient()
                .performRequest(HttpMethod.GET.toString(), "/_aliases", Collections.emptyMap());
            final String entity = EntityUtils.toString(response.getEntity());
            final Map<String, Object> indices = mapper
                .readValue(entity, new TypeReference<Map<String, Object>>() {
                });

            return indices.keySet();
        } catch (IOException e) {
            throw new ElasticsearchException(e);
        }
    }
}
