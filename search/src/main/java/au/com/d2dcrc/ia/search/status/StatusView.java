package au.com.d2dcrc.ia.search.status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.time.Instant;
import org.springframework.http.HttpStatus;

/**
 * Defines a common response body format for Data API responses that do not include entity data.
 *
 * <p>This includes operations that do not return a value (e.g. delete) and error conditions (via {@link ErrorView}) and
 * operations.
 */
@ApiModel
public class StatusView {

    private final HttpStatus status;

    @ApiModelProperty(name = "Request path", required = true, example = "/api/v1/mycollection/123")
    private final String path;

    @ApiModelProperty(name = "Response timestamp", required = true, example = "2016-05-25T16:77:25.365Z")
    private final Instant timestamp;

    /**
     * Construct a status response body.
     *
     * @param path API path for request that generated the error
     * @param status HTTP status
     */
    public StatusView(
        final String path,
        final HttpStatus status
    ) {
        this.path = path;
        this.status = status;
        this.timestamp = Instant.now();
    }

    /**
     * Get the HTTP status code.
     *
     * @return the http status code
     */
    @ApiModelProperty(name = "HTTP status code", required = true, example = "200")
    public int getStatus() {
        return status.value();
    }

    /**
     * Get the HTTP status reason phrase.
     *
     * @return the HTTP reason
     */
    @ApiModelProperty(name = "HTTP status reason", required = true, example = "OK")
    public String getReason() {
        return status.getReasonPhrase();
    }

    /**
     * Get the relative API path for request that generated the error.
     *
     * @return the path of the API request
     */
    public String getPath() {
        return path;
    }

    /**
     * Get the timestamp at which error occurred.
     *
     * @return the timestamp of the error
     */
    public Instant getTimestamp() {
        return timestamp;
    }

}
