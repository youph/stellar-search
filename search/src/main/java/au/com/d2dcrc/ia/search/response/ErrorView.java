package au.com.d2dcrc.ia.search.response;

import java.time.Instant;
import org.springframework.http.HttpStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Defines a common response body format for all error responses made by the
 * Data API.
 */
@ApiModel
public class ErrorView {

    private final String path;
    private final HttpStatus status;
    private final String reason;
    private final Throwable error;
    private final String message;
    private final Instant timestamp;

    /**
     * Construct an error response body.
     *
     * @param path
     *            API path for request that generated the error
     * @param status
     *            HTTP status
     * @param error
     *            error that caused the error
     */
    public ErrorView(final String path, final HttpStatus status, final Throwable error) {
        this.path = path;
        this.status = status;
        this.reason = status.getReasonPhrase();
        this.error = error;
        this.message = error.getLocalizedMessage();
        this.timestamp = Instant.now();
    }

    /**
     * Get the relative API path for request that generated the error.
     *
     * @return the path of the API request
     */
    @ApiModelProperty(name = "Request path", required = true, example = "/api/v1/mycollection/123")
    public String getPath() {
        return path;
    }

    @ApiModelProperty(name = "HTTP status code", required = true, example = "400")
    public int getStatus() {
        return status.value();
    }

    @ApiModelProperty(name = "HTTP status reason", required = true, example = "Bad Request")
    public String getReason() {
        return reason;
    }

    /**
     * Get the error class name.
     *
     * @return the error class name
     */
    @ApiModelProperty(name = "Error name", example = "java.lang.RuntimeException")
    public String getError() {
        return error.getClass().getName();
    }

    /**
     * Get the localised error message.
     *
     * @return error message
     */
    @ApiModelProperty(name = "Error message", example = "An error occurred")
    public String getMessage() {
        return message;
    }

    /**
     * Get the timestamp at which error occurred.
     *
     * @return the timestamp of the error
     */
    @ApiModelProperty(name = "Response timestamp", required = true, example = "2016-05-25T16:77:25.365Z")
    public Instant getTimestamp() {
        return timestamp;
    }

}