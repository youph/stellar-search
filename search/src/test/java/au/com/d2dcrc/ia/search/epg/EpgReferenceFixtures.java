package au.com.d2dcrc.ia.search.epg;

import au.com.d2dcrc.ia.search.management.EpgReferenceModel;

import java.net.URI;

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
        URI.create(EpgReferenceFixtures.class.getResource("imdb-edges.json").toString())
    );

    /**
     * Fixture for the IMDB EPG reference json.
     *
     * <p>We can't read this from a resource since the fully qualified URI to the embedded IMDB
     *  EPG cannot be known easily (could use have maven filter the resource with
     * {@code ${project.baseUri}} but this is easier, and doesn't require changes to the pom.xml)
     */
    public static final String IMDB_EPG_REFERENCE_JSON = (
        "{\n" +
        "    \"graphs\": \"${graphs}\",\n" +
        "    \"edges\": \"${edges}\",\n" +
        "    \"vertices\": \"${vertices}\"\n" +
        "}\n"
    )
        .replace("${graphs}", EpgReferenceFixtures.class.getResource("imdb-graphs.json").toString())
        .replace("${vertices}", EpgReferenceFixtures.class.getResource("imdb-vertices.json").toString())
        .replace("${edges}", EpgReferenceFixtures.class.getResource("imdb-edges.json").toString());

}
