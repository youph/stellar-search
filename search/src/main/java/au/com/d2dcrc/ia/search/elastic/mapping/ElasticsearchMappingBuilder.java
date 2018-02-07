package au.com.d2dcrc.ia.search.elastic.mapping;

import au.com.d2dcrc.ia.search.management.EpgSchema;
import au.com.d2dcrc.ia.search.management.GraphSchemaClass;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Generate mappings to create elasticsearch indices.
 */
@Component
public class ElasticsearchMappingBuilder {

    private static final Logger logger = LoggerFactory.getLogger(ElasticsearchMappingBuilder.class);
    private static final String MAPPINGS_FIELD = "mappings";
    private static final String DOCUMENT_FIELD = "doc";
    private static final String PROPERTIES_FIELD = "properties";
    private static final String IN_FIELD = "in";
    private static final String OUT_FIELD = "out";
    private static final String LABEL_FIELD = "label";
    private static final String TYPE_FIELD = "type";
    private static final String GRAPHS_FIELD = "graphs";
    private static final String ID_FIELD = "id";
    private static final String SRC_FIELD = "node";
    private static final String DEST_FIELD = "node";
    private final ObjectMapper mapper;

    /**
     * Constructor.
     *
     * @param mapper The JSON mapper
     */
    @Inject
    public ElasticsearchMappingBuilder(ObjectMapper mapper) {
        this.mapper = mapper;
    }

    /**
     * Creates a default elasticsearch mapping.
     *
     * @return an elasticsearch mapping as json.
     */
    public String createMapping() throws IllegalStateException {
        final String result;

        try {
            result = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(createDefaultMapping());
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }

        return result;
    }

    /**
     * Creates an elasticsearch mapping from {@link EpgSchema} object.
     *
     * @param epgSchema the EPG schema
     * @return an elasticsearch mapping as json
     */
    public String createMapping(EpgSchema epgSchema) throws IllegalStateException {
        final ObjectNode rootNode = createDefaultMapping();

        // Collect all properties from the EPG schema classes.
        HashMap<String, String> allNodeProperties = new HashMap<>();
        for (GraphSchemaClass schemaClass : epgSchema.getGraphSchema().getClasses()) {
            allNodeProperties.putAll(schemaClass.getProperties());
        }

        // Add properties to the root node.
        ObjectNode rootNodeProperties = rootNode.with(MAPPINGS_FIELD).with(DOCUMENT_FIELD)
            .with(PROPERTIES_FIELD).with(PROPERTIES_FIELD).with(PROPERTIES_FIELD);
        for (Map.Entry<String, String> entry : allNodeProperties.entrySet()) {
            addProperty(rootNodeProperties, entry.getKey(), entry.getValue());
        }

        // Add properties to the dest node.
        ObjectNode destNodeProperties = rootNode.with(MAPPINGS_FIELD).with(DOCUMENT_FIELD)
            .with(PROPERTIES_FIELD).with(OUT_FIELD).with(PROPERTIES_FIELD).with(DEST_FIELD)
            .with(PROPERTIES_FIELD).with(PROPERTIES_FIELD).with(PROPERTIES_FIELD);
        for (Map.Entry<String, String> entry : allNodeProperties.entrySet()) {
            addProperty(destNodeProperties, entry.getKey(),
                entry.getValue());
        }

        // Add properties to the src node.
        ObjectNode srcNodeProperties = rootNode.with(MAPPINGS_FIELD).with(DOCUMENT_FIELD)
            .with(PROPERTIES_FIELD).with(IN_FIELD).with(PROPERTIES_FIELD).with(DEST_FIELD)
            .with(PROPERTIES_FIELD).with(PROPERTIES_FIELD).with(PROPERTIES_FIELD);
        for (Map.Entry<String, String> entry : allNodeProperties.entrySet()) {
            addProperty(srcNodeProperties, entry.getKey(),
                entry.getValue());
        }

        final String result;

        try {
            result = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(rootNode);
        } catch (JsonProcessingException e) {
            logger.error(e.getMessage());
            throw new IllegalStateException(e);
        }

        return result;
    }

    private ObjectNode createDefaultMapping() {
        // The root node
        ObjectNode rootNode = mapper.createObjectNode();
        rootNode.putObject(MAPPINGS_FIELD);
        ObjectNode documentNode = createNodeWithProperties(rootNode.with(MAPPINGS_FIELD),
            DOCUMENT_FIELD, false);

        addProperty(documentNode.with(PROPERTIES_FIELD), LABEL_FIELD,
            ElasticsearchMappingType.KEYWORD);
        addProperty(documentNode.with(PROPERTIES_FIELD), GRAPHS_FIELD,
            ElasticsearchMappingType.KEYWORD);

        // The root node fields
        createNodeWithProperties(documentNode.with(PROPERTIES_FIELD),
            PROPERTIES_FIELD,
            false);

        // The in relation from the root node
        ObjectNode inNode = createNodeWithProperties(documentNode.with(PROPERTIES_FIELD),
            IN_FIELD,
            true);
        addProperty(inNode.with(PROPERTIES_FIELD), LABEL_FIELD, ElasticsearchMappingType.KEYWORD);
        createNodeWithProperties(inNode.with(PROPERTIES_FIELD),
            PROPERTIES_FIELD,
            false);

        // The source node (in)
        ObjectNode srcNode = createNodeWithProperties(inNode.with(PROPERTIES_FIELD), SRC_FIELD,
            false);
        addProperty(srcNode.with(PROPERTIES_FIELD), ID_FIELD, ElasticsearchMappingType.KEYWORD);
        addProperty(srcNode.with(PROPERTIES_FIELD), LABEL_FIELD, ElasticsearchMappingType.KEYWORD);
        addProperty(srcNode.with(PROPERTIES_FIELD), GRAPHS_FIELD,
            ElasticsearchMappingType.KEYWORD);

        // The source node fields
        createNodeWithProperties(srcNode.with(PROPERTIES_FIELD),
            PROPERTIES_FIELD, false);

        // The out relation from the root node
        ObjectNode outNode = createNodeWithProperties(documentNode.with(PROPERTIES_FIELD),
            OUT_FIELD,
            true);
        addProperty(outNode.with(PROPERTIES_FIELD), LABEL_FIELD, ElasticsearchMappingType.KEYWORD);
        createNodeWithProperties(outNode.with(PROPERTIES_FIELD),
            PROPERTIES_FIELD,
            false);

        // The destination node (out)
        ObjectNode destNode = createNodeWithProperties(outNode.with(PROPERTIES_FIELD), DEST_FIELD,
            false);
        addProperty(destNode.with(PROPERTIES_FIELD), ID_FIELD, ElasticsearchMappingType.KEYWORD);
        addProperty(destNode.with(PROPERTIES_FIELD), LABEL_FIELD, ElasticsearchMappingType.KEYWORD);
        addProperty(destNode.with(PROPERTIES_FIELD), GRAPHS_FIELD,
            ElasticsearchMappingType.KEYWORD);

        // The destination node fields
        createNodeWithProperties(destNode.with(PROPERTIES_FIELD),
            PROPERTIES_FIELD, false);

        return rootNode;
    }

    private void addProperty(ObjectNode node, String name, String type) {
        if (node != null) {
            node.putObject(name).put(TYPE_FIELD, convertType(type));
        }
    }

    private void addProperty(ObjectNode node, String name, ElasticsearchMappingType type) {
        if (node != null) {
            node.putObject(name).put(TYPE_FIELD, type.toString().toLowerCase());
        }
    }

    private ObjectNode createNodeWithProperties(ObjectNode node, String name, boolean isNested) {
        ObjectNode newNode = node.putObject(name);
        if (isNested) {
            newNode.put(TYPE_FIELD, "nested");
        }
        newNode.putObject(PROPERTIES_FIELD);

        return newNode;
    }

    private String convertType(String type) {
        switch (type.toLowerCase()) {
            case "string":
                return "text";
            default:
                return type.toLowerCase();
        }
    }
}
