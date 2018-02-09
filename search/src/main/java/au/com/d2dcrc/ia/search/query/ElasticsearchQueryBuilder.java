package au.com.d2dcrc.ia.search.query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ElasticsearchQueryBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchQueryBuilder.class);

    private ObjectMapper mapper;

    /**
     * Constructor.
     *
     * @param mapper JSON mapper
     */
    public ElasticsearchQueryBuilder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Create an elasticsearch query from a {@link QueryRequestModel} object.
     *
     * @param model the user query model
     * @return elasticsearch query as a string
     */
    public String toElasticsearchQueryString(QueryRequestModel model) {
        final ObjectNode rootNode = mapper.createObjectNode();
        rootNode.putObject("query").putObject("bool");

        //todo can label be null in user query - or consider invalid??

        final ArrayNode filterNode = rootNode.with("query").with("bool").putArray("filter");
        filterNode.add(createTermQueryNode("label", model.getNode().getLabel().toString()));

        final ArrayNode mustArray = rootNode.with("query").with("bool").putArray("must");

        // Add node properties
        if (!model.getNode().getProperties().isEmpty()) {
            for (Map.Entry<String, JsonNode> entry : model.getNode().getProperties().entrySet()) {
                mustArray.add(createMatchQueryNode(
                    "properties." + entry.getKey(),
                    entry.getValue().textValue()
                ));
            }
        }

        // Add relations.
        if (model.getNode().getRelations() != null) {
            final ObjectNode relationNode = mapper.createObjectNode();
            relationNode.putObject("nested");
            mustArray.add(relationNode);

            for (QueryRequestNodeRelation relation : model.getNode().getRelations()) {
                // Add relation direction.
                relationNode.with("nested").put("path", relation.getDirection().toString());
                relationNode.with("nested").putObject("query");

                if (relation.getLabel() != null || relation.getNode() != null) {
                    relationNode.with("nested").with("query").putObject("bool");
                    final ArrayNode relMustArray =
                        relationNode.with("nested").with("query").with("bool").putArray("must");

                    // Add relation label.
                    if (relation.getLabel() != null) {
                        relMustArray.add(createTermQueryNode(
                            relation.getDirection().toString() + ".label",
                            relation.getLabel().toString()
                        ));
                    }

                    // Add relation node properties.
                    if (relation.getNode() != null) {
                        if (!relation.getNode().getProperties().isEmpty()) {
                            for (Map.Entry<String, JsonNode> entry : relation.getNode().getProperties().entrySet()) {
                                relMustArray.add(createMatchQueryNode(
                                    relation.getDirection().toString() + ".node.properties." + entry.getKey(),
                                    entry.getValue().textValue()
                                ));
                            }
                        }
                    }
                } else {
                    // Relation label is not specified and no connected node.
                    // ie. match_all relations
                    relationNode.with("nested").with("query").putObject("match_all");
                }

                // Add inner_hits
                relationNode.with("nested").putObject("inner_hits");
            }
        }

        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            return null;
        }
    }

    private ObjectNode createTermQueryNode(String field, String value) {
        final ObjectNode termNode = mapper.createObjectNode();
        termNode.putObject("term").put(field, value);
        return termNode;
    }

    private ObjectNode createMatchQueryNode(String field, String value) {
        final ObjectNode termNode = mapper.createObjectNode();
        termNode.putObject("match").put(field, value);
        return termNode;
    }
}
