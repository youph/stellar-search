package au.com.d2dcrc.ia.search.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

/**
 * Utility for extracting information from an incoming request.
 */
public abstract class RequestInfo {

    private RequestInfo() {}

    /**
     * Return the remote IP address that issued the given request. Will use the X-Real-IP first else X-Forwarded-For,
     * in the event that the request was proxied in some way (which is fairly common), and fallback to the remote
     * address in the request if this is not available.
     *
     * <p>This is very useful for logging and banning failed login attempt IP addresses.
     *
     * @param servletRequest the Http request
     * @return the IP address of the remote that initiated the request
     */
    public static String getRemoteIpAddress(final HttpServletRequest servletRequest) {

        // try real ip
        String ipAddress = servletRequest.getHeader("X-Real-IP");

        // no real ip, try forwarded for
        if (ipAddress == null) {
            final String forwardedFor = servletRequest.getHeader("X-Forwarded-For");
            if (forwardedFor != null) {
                ipAddress = forwardedFor.split("\\s*,\\s*", 2)[0];
            }
        }

        // failing all that fall back on the request's remote address
        if (ipAddress == null) {
            ipAddress = servletRequest.getRemoteAddr();
        }

        return ipAddress;

    }

    /**
     * Obtains the URI path of the request.
     * 
     * @param request - The HTTP request.
     * @return The URI path string.
     */
    public static String getPath(HttpServletRequest request) {
        return request.getRequestURI();
    }

    /**
     * Obtains the HTTP status of the request.
     * 
     * @param request - The HTTP request.
     * @return The HTTP status code.
     */
    public static HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode != null) {
            try {
                return HttpStatus.valueOf(statusCode);
            } catch (IllegalArgumentException ex) {
                // Fall through...
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }

    /**
     * Indicates whether or not to include error trace information.
     * 
     * @param request - The HTTP request.
     * @return A value of true (or false) if the trace is (or is not) to be made available.
     */
    public static boolean isTraceMode(HttpServletRequest request) {
        String parameter = request.getParameter("trace");
        return (parameter == null) ? false : !"false".equals(parameter.toLowerCase());
    }

}
