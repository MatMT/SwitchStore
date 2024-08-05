package com.dwf.switchstore.ws.interceptor;

import com.dwf.switchstore.ws.util.JwtUtil;
import jakarta.interceptor.AroundInvoke;
import jakarta.interceptor.Interceptor;
import jakarta.interceptor.InvocationContext;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

/**
 * This class provides an interceptor for authenticating users
 */
@Secure // Indicates that this class is a Secure
@Interceptor // Indicates that this class is an interceptor
public class AuthenticationInterceptor {

    @Context
    private HttpHeaders headers; // The HTTP headers of the request being intercepted

    /**
     * Validates the JWT token in the Authorization header
     *
     * @param context the context of the method being intercepted
     * @return the result of the method being intercepted
     * @throws Exception if an error occurs during the interception
     */
    @AroundInvoke // Indicates that this method should be called before and after the method it intercepts
    public Object secure(InvocationContext context) throws Exception {
        String authHeader = headers.getHeaderString(HttpHeaders.AUTHORIZATION);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Missing or invalid Authorization header\"}")
                    .build());
        }

        String token = authHeader.substring(7); // Remove "Bearer " prefix
        if (!JwtUtil.validateToken(token)) {
            throw new WebApplicationException(Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"message\": \"Invalid or expired token\"}")
                    .build());
        }

        return context.proceed();
    }
}