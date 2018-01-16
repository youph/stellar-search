package au.com.d2dcrc.ia.search.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.List;
import javax.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

/**
 * View of a query result returned to the user.
 */
@ApiModel
@Validated
public class QueryResultView {

    @ApiModelProperty
    @NotNull
    private final int total;

    @ApiModelProperty
    private final int maxScore;

    @ApiModelProperty
    private List<QueryResultHit> hits;

    /**
     * Constructor.
     *
     * @param total the total number of hits
     * @param maxScore the highest scoring hit
     * @param hits a list of hits
     */
    public QueryResultView(
        final int total,
        final int maxScore,
        final List<QueryResultHit> hits
    ) {
        this.total = total;
        this.maxScore = maxScore;
        this.hits = hits;
    }

    public int getTotal() {
        return total;
    }

    public int getMaxScore() {
        return maxScore;
    }

    public List<QueryResultHit> getHits() {
        return hits;
    }
}
