package au.com.d2dcrc.ia.search.status;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.http.HttpStatus;

/**
 * Defines a common response body format for all error responses made by the Data API.
 */
@ApiModel
public class ErrorView extends StatusView {

    private final HttpStatus status;
    private final Exception exception;

    /**
     * Construct an error response body.
     *
     * @param path      API path for request that generated the error
     * @param status    HTTP status
     * @param exception exception that caused the error
     */
    public ErrorView(
        final String path,
        final HttpStatus status,
        final Exception exception
    ) {
        super(path, status);
        this.status = status;
        this.exception = exception;
    }


    @ApiModelProperty(name = "HTTP status code", required = true, example = "400")
    @Override
    public int getStatus() {
        return status.value();
    }

    @ApiModelProperty(name = "HTTP status reason", required = true, example = "Bad Request")
    @Override
    public String getReason() {
        return status.getReasonPhrase();
    }

    /**
     * Get the exception class name.
     *
     * @return the exception class name
     */
    @ApiModelProperty(name = "Exception name", example = "java.lang.RuntimeException")
    public String getException() {
        return exception.getClass().getName();
    }

    /**
     * Get the localised exception message.
     *
     * @return exception message
     */
    @ApiModelProperty(name = "Exception message", example = "An error occurred")
    public String getExceptionMessage() {
        return exception.getLocalizedMessage();
    }

}
