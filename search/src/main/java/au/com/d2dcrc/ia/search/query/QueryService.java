package au.com.d2dcrc.ia.search.query;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service for handling search queries.
 */
@Service
public class QueryService {

    private static final Logger logger = LoggerFactory.getLogger(QueryService.class);

    /**
     * Respond to a search query.
     *
     * @param queryModel the query request supplied by the user
     * @return the query result
     */
    public QueryResultView search(QueryRequestModel queryModel) {
        /**
         * Mock response.
         */
        QueryResultHit hit1 = new QueryResultHit(
            1,
            ImdbNodeLabel.Person,
            "0C6C723C0BED4738B0254580CC5F05B8",
            ImmutableMap.of("name", "Clint Eastwood"),
            ImmutableList.of(new QueryResultHitRelation("", ImdbRelationLabel.actedin, "", "")));

        QueryResultHit hit2 = new QueryResultHit(
            1,
            ImdbNodeLabel.Person,
            "B89531B1276045EAA0B88E3FEA58FAD1",
            ImmutableMap.of("name", "Clint James"),
            ImmutableList.of(new QueryResultHitRelation("", ImdbRelationLabel.actedin, "", "")));

        QueryResultHit hit3 = new QueryResultHit(
            1,
            ImdbNodeLabel.Person,
            "0407FD2D4EA04EAE83EAAC2A13D5DA83",
            ImmutableMap.of("name", "Clint Walker"),
            ImmutableList.of(new QueryResultHitRelation("", ImdbRelationLabel.actedin, "", "")));

        QueryResultView view = new QueryResultView(
            3,
            1,
            ImmutableList.of(hit1, hit2, hit3));

        return view;
    }
}
