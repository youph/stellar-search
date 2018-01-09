package au.com.d2dcrc.ia.search.common;

import javax.servlet.http.HttpServletRequest;

/**
 * Utility for extracting IP from an incoming request.
 */
public class RequestInfo {

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
}
