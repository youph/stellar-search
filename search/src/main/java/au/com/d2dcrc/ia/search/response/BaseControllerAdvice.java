package au.com.d2dcrc.ia.search.response;

import au.com.d2dcrc.ia.search.common.RequestInfo;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Base class to help map exceptions into responses.
 */
public class BaseControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Provides a standard response to any exception.
     * 
     * @param request - The HTTP request.
     * @param ex - The exception thrown.
     * @return The error response.
     */
    protected ResponseEntity<ErrorView> errorResponse(HttpServletRequest request, Throwable ex) {
        HttpStatus status = RequestInfo.getStatus(request);
        return new ResponseEntity<>(new ErrorView(RequestInfo.getPath(request), status, ex), status);
    }

    /**
     * Provides a response to any exception with a non-standard status code.
     *  
     * @param request - The HTTP request.
     * @param status - The status code of the response.
     * @param ex - The exception thrown.
     * @return The error response.
     */
    protected ResponseEntity<ErrorView> errorResponse(HttpServletRequest request, HttpStatus status, Throwable ex) {
        return new ResponseEntity<>(new ErrorView(RequestInfo.getPath(request), status, ex), status);
    }

    /**
     * Provides a response to any exception.
     *  
     * @param path - The URI path of the request.
     * @param status - The status code of the response.
     * @param ex - The exception thrown.
     * @return The error response.
     */
    protected ResponseEntity<ErrorView> errorResponse(String path, HttpStatus status, Throwable ex) {
        return new ResponseEntity<>(new ErrorView(path, status, ex), status);
    }

}