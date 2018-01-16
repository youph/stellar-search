package au.com.d2dcrc.ia.search.query;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.CharStreams;
import com.google.common.io.Resources;
import java.io.IOException;
import java.net.URL;
import javax.inject.Inject;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.InputStreamReader;

@RunWith(SpringRunner.class)
@JsonTest
public class QueryRequestModelTest {

    private final static Logger logger = LoggerFactory.getLogger(QueryRequestModelTest.class);

    @Inject
    private ObjectMapper mapper;

    @Test
    public void testQuery001() throws IOException {
        final URL jsonResource = getClass().getResource("query001.json");

        QueryRequestModel queryModel = mapper
            .readValue(jsonResource, QueryRequestModel.class);

        Assert.assertNotNull(queryModel);
        Assert.assertEquals(queryModel.getNode().getLabel(), ImdbNodeLabel.Person);
        Assert.assertEquals(queryModel.getNode().getProperties().get("name"), "Clint");
    }

    @Test
    public void testQuery002() throws IOException {
        final URL jsonResource = getClass().getResource("query002.json");

        QueryRequestModel queryModel = mapper
            .readValue(jsonResource, QueryRequestModel.class);

        Assert.assertNotNull(queryModel);
        Assert.assertEquals(queryModel.getNode().getLabel(), ImdbNodeLabel.Person);
        Assert.assertEquals(queryModel.getNode().getProperties().get("name"), "Clint");
        Assert
            .assertEquals(queryModel.getNode().getRelation().getDirection(), RelationDirection.out);
        Assert
            .assertEquals(queryModel.getNode().getRelation().getLabel(), ImdbRelationLabel.actedin);
    }

    @Test
    public void testQuery004() throws IOException {
        final URL jsonResource = getClass().getResource("query004.json");

        QueryRequestModel queryModel = mapper
            .readValue(jsonResource, QueryRequestModel.class);

        Assert.assertNotNull(queryModel);
        Assert.assertEquals(queryModel.getNode().getLabel(), ImdbNodeLabel.Film);
        Assert.assertEquals(queryModel.getNode().getProperties().size(), 0);
        Assert
            .assertEquals(queryModel.getNode().getRelation().getDirection(), RelationDirection.in);
        Assert
            .assertEquals(queryModel.getNode().getRelation().getLabel(), ImdbRelationLabel.actedin);
        Assert.assertEquals(queryModel.getNode().getRelation().getNode().getLabel(),
            ImdbNodeLabel.Person);
        Assert
            .assertEquals(queryModel.getNode().getRelation().getNode().getProperties().get("name"),
                "Clint Eastwood");
    }

    @Test
    public void testQuery005() throws IOException {
        final URL jsonResource = getClass().getResource("query005.json");

        QueryRequestModel queryModel = mapper
            .readValue(jsonResource, QueryRequestModel.class);

        Assert.assertNotNull(queryModel);
        Assert.assertEquals(queryModel.getNode().getLabel(), ImdbNodeLabel.Film);
        Assert.assertEquals(queryModel.getNode().getProperties().get("classification"), "Thriller");
        Assert
            .assertEquals(queryModel.getNode().getRelation().getDirection(), RelationDirection.in);
        Assert
            .assertEquals(queryModel.getNode().getRelation().getLabel(), ImdbRelationLabel.actedin);
        Assert.assertEquals(queryModel.getNode().getRelation().getNode().getLabel(),
            ImdbNodeLabel.Person);
        Assert
            .assertEquals(queryModel.getNode().getRelation().getNode().getProperties().get("name"),
                "Clint Eastwood");
    }
}
