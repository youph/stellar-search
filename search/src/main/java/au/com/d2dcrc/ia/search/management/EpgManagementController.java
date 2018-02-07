package au.com.d2dcrc.ia.search.management;

import au.com.d2dcrc.ia.search.response.ErrorView;
import au.com.d2dcrc.ia.search.response.NoView;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ResponseHeader;

@Api("Management of Extended Property Graphs (EPGs)")
@RestController
@RequestMapping(
    value = "/api/v1.0/indexes",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
)
public class EpgManagementController {

    private static final Logger logger = LoggerFactory.getLogger(EpgManagementController.class);

    private final EpgManagementService managementService;

    /**
     * Constructor for a REST Service for managing EPG Indexes.
     *
     * @param managementService the task service to injected into the controller
     */
    public EpgManagementController(final EpgManagementService managementService) {
        this.managementService = managementService;
    }

    /**
     * Get a list of Extended Property Graphs Meta data views.
     *
     * @return a list of EPG meta views
     */
    @ApiOperation("Retrieve all EPGs")
    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<EpgMetaView> listAllEpgs() {
        logger.debug("Fetching all EPGs");
        return managementService.findAllEpgs();
    }

    /**
     * Get a view of an EPG meta data.
     *
     * @param name the name of the EPG to lookup
     * @return a HTTP 200 with the view if found, else HTTP 404 Not found
     */
    @ApiOperation("Retrieve an EPG")
    @RequestMapping(value = "/{name}", method = RequestMethod.GET)
    public ResponseEntity<EpgMetaView> listEpg(
        @ApiParam(value = "EPG index name", required = true)
        @PathVariable("name")
        final String name
    ) {
        logger.debug("Fetching EPG: {}", name);
        final EpgMetaView view = managementService.findEpg(name);
        if (view == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(view, HttpStatus.OK);
    }

    /**
     * Creates an EPG. The specified name must not exist.
     *
     * @param name the name of the EPG index to create
     * @param epgReference references to the resources the describe the EPG
     * @return a view of the created EPG meta data if successful,
     *          or a HTTP 400 Bad Request status if failed.
     */
    @ApiOperation(
        value = "Create an EPG",
        code = org.apache.http.HttpStatus.SC_CREATED,
        responseHeaders = @ResponseHeader(name = HttpHeaders.LOCATION)
    )
    @ApiResponses(value = { 
        @ApiResponse(
            code = org.apache.http.HttpStatus.SC_CREATED, 
            message = "Created", 
            response = EpgMetaView.class
        ), 
        @ApiResponse(
            code = org.apache.http.HttpStatus.SC_BAD_REQUEST, 
            message = "Bad Request", 
            response = ErrorView.class
        ),
        @ApiResponse(
            code = org.apache.http.HttpStatus.SC_INTERNAL_SERVER_ERROR,
            message = "Internal Server Error",
            response = ErrorView.class
        )
    })
    @ResponseStatus(HttpStatus.CREATED)
    @RequestMapping(value = "/{name}", method = RequestMethod.POST)
    public ResponseEntity<EpgMetaView> createEpg(
        @ApiParam(value = "EPG index name", required = true)
        @NotEmpty
        @Valid
        @PathVariable
        final String name,
        @ApiParam(required = true)
        @Valid
        @RequestBody
        final EpgReferenceModel epgReference
    ) {
        logger.debug("Creating new EPG: {}", name);
        final EpgMetaView newEpg = managementService.createEpg(name, epgReference);

        final URI location = ServletUriComponentsBuilder.fromCurrentRequest()
            .build()
            .toUri();

        final HttpHeaders headers = new HttpHeaders();
        headers.setLocation(location);
        return new ResponseEntity<>(newEpg, headers, HttpStatus.CREATED);
    }

    /**
     * Deletes an EPG index.
     *
     * @param name
     *            the name of the EPG index to delete
     * @return HTTP 204 No Content on success, or 400 Bad request on failure.
     */
    @ApiOperation(value = "Delete an EPG", code = org.apache.http.HttpStatus.SC_NO_CONTENT)
    @ApiResponses(value = { 
        @ApiResponse(
            code = org.apache.http.HttpStatus.SC_NO_CONTENT, 
            message = "No Content",
            response = NoView.class
        ), 
        @ApiResponse(
            code = org.apache.http.HttpStatus.SC_BAD_REQUEST, 
            message = "Bad Request", 
            response = ErrorView.class
        ) 
    })
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = "/{name}", method = RequestMethod.DELETE)
    public ResponseEntity<?> deleteEpg(
        @ApiParam(value = "EPG index name", required = true)
        @PathVariable
        final String name
    ) {
        logger.debug("Deleting EPG: {}", name);
        managementService.deleteEpg(name);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
