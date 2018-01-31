package au.com.d2dcrc.ia.search.query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "Make graph search queries")
@RestController
@RequestMapping(
    value = "/api/v1.0/search",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class QueryRestController {

    private static final Logger logger = LoggerFactory.getLogger(QueryRestController.class);

    private final QueryService queryService;

    /**
     * Constructor.
     *
     * @param queryService the service to inject into the controller.
     */
    public QueryRestController(QueryService queryService) {
        this.queryService = queryService;
    }

    /**
     * Performs a query.
     *
     * @param queryModel user supplied query
     * @return a query result
     */
    @ApiOperation("Perform a search")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ResponseEntity<QueryResultView> search(
        @RequestBody
        @Valid
        QueryRequestModel queryModel
    ) {
        QueryResultView view = queryService.search(queryModel);

        return new ResponseEntity<>(view, HttpStatus.OK);
    }
}
