package au.com.d2dcrc.ia.search.epg;

import au.com.d2dcrc.ia.search.management.EpgReferenceModel;
import au.com.d2dcrc.ia.search.management.EpgSchema;
import au.com.d2dcrc.ia.search.management.GraphSchema;
import au.com.d2dcrc.ia.search.management.GraphSchemaClass;
import au.com.d2dcrc.ia.search.management.GraphSchemaClassLink;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import java.net.URI;
import javax.inject.Inject;

/**
 * Fixture for EPG reference
 */
public class EpgReferenceFixtures {
    /**
     * Fixture for the IMDB EPG reference model
     */
    public static final EpgReferenceModel IMDB_EPG_REFERENCE_MODEL = new EpgReferenceModel(
        URI.create(EpgReferenceFixtures.class.getResource("imdb-graphs.json").toString()),
        URI.create(EpgReferenceFixtures.class.getResource("imdb-vertices.json").toString()),
        URI.create(EpgReferenceFixtures.class.getResource("imdb-edges.json").toString()),
        null
    );

    /**
     * Fixture for the IMDB EPG reference model with example graph schema information
     */
    public static final EpgReferenceModel IMDB_EPG_REFERENCE_MODEL_GRAPH_SCHEMA = new EpgReferenceModel(
        URI.create(EpgReferenceFixtures.class.getResource("imdb-graphs.json").toString()),
        URI.create(EpgReferenceFixtures.class.getResource("imdb-vertices.json").toString()),
        URI.create(EpgReferenceFixtures.class.getResource("imdb-edges.json").toString()),
        new EpgSchema(
            ImmutableList.of(
                URI.create("films.csv"),
                URI.create("persons.csv"),
                URI.create("corps.csv"),
                URI.create("producers.csv"),
                URI.create("actors.csv"),
                URI.create("staff.csv")),
            new GraphSchema(
                ImmutableList.of(
                    new GraphSchemaClass(
                        "Film",
                        ImmutableMap.of(
                            "title", "string",
                            "year", "string",
                            "classification", "string",
                            "isforeign", "boolean")),
                    new GraphSchemaClass(
                        "Person",
                        ImmutableMap.of(
                            "name", "string"
                        )
                    ),
                    new GraphSchemaClass(
                        "Company",
                        ImmutableMap.of(
                            "name", "string"
                        )
                    )
                ),
                ImmutableList.of(
                    new GraphSchemaClassLink("actedin", "Person", "Film"),
                    new GraphSchemaClassLink("workedin", "Person", "Film"),
                    new GraphSchemaClassLink("produced", "Company", "Film"))
            )
        )
    );

    /**
     * Fixture for the IMDB EPG reference json.
     *
     * <p>We can't read this from a resource since the fully qualified URI to the embedded IMDB EPG
     * cannot be known easily (could use have maven filter the resource with {@code
     * ${project.baseUri}} but this is easier, and doesn't require changes to the pom.xml)
     */
    public static final String IMDB_EPG_REFERENCE_JSON = (
        "{\n" +
            "    \"graphs\": \"${graphs}\",\n" +
            "    \"edges\": \"${edges}\",\n" +
            "    \"vertices\": \"${vertices}\"\n" +
            "}\n"
    )
        .replace("${graphs}", EpgReferenceFixtures.class.getResource("imdb-graphs.json").toString())
        .replace("${vertices}",
            EpgReferenceFixtures.class.getResource("imdb-vertices.json").toString())
        .replace("${edges}", EpgReferenceFixtures.class.getResource("imdb-edges.json").toString());

    /**
     * Fixture for IMDB EPG reference with example graph schema information.
     */
    public static final String IMDB_EPG_REFERENCE_GRAPH_SCHEMA_JSON = (
        "{\n"
            + "  \"graphs\": \"${graphs}\",\n"
            + "  \"vertices\": \"${vertices}\",\n"
            + "  \"edges\": \"${edges}\",\n"
            + "  \"epgSchema\": {\n"
            + "    \"sources\": [\n"
            + "      \"films.csv\",\n"
            + "      \"persons.csv\",\n"
            + "      \"corps.csv\",\n"
            + "      \"producers.csv\",\n"
            + "      \"actors.csv\",\n"
            + "      \"staff.csv\"\n"
            + "    ],\n"
            + "    \"graphSchema\": {\n"
            + "      \"classes\": [\n"
            + "        {\n"
            + "          \"name\": \"Film\",\n"
            + "          \"props\": {\n"
            + "            \"title\": \"string\",\n"
            + "            \"year\": \"string\",\n"
            + "            \"classification\": \"string\",\n"
            + "            \"isforeign\": \"boolean\"\n"
            + "          }\n"
            + "        },\n"
            + "        {\n"
            + "          \"name\": \"Person\",\n"
            + "          \"props\": {\n"
            + "            \"name\": \"string\"\n"
            + "          }\n"
            + "        },\n"
            + "        {\n"
            + "          \"name\": \"Company\",\n"
            + "          \"props\": {\n"
            + "            \"name\": \"string\"\n"
            + "          }\n"
            + "        }\n"
            + "      ],\n"
            + "      \"classLinks\": [\n"
            + "        {\n"
            + "          \"name\": \"actedin\",\n"
            + "          \"source\": \"Person\",\n"
            + "          \"target\": \"Film\"\n"
            + "        },\n"
            + "        {\n"
            + "          \"name\": \"workedin\",\n"
            + "          \"source\": \"Person\",\n"
            + "          \"target\": \"Film\"\n"
            + "        },\n"
            + "        {\n"
            + "          \"name\": \"produced\",\n"
            + "          \"source\": \"Company\",\n"
            + "          \"target\": \"Film\"\n"
            + "        }\n"
            + "      ]\n"
            + "    }\n"
            + "  }\n"
            + "}\n"
    )
        .replace("${graphs}", EpgReferenceFixtures.class.getResource("imdb-graphs.json").toString())
        .replace("${vertices}",
            EpgReferenceFixtures.class.getResource("imdb-vertices.json").toString())
        .replace("${edges}", EpgReferenceFixtures.class.getResource("imdb-edges.json").toString());
}
