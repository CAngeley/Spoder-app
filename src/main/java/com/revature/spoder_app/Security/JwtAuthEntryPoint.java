package com.revature.spoder_app.Security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * This class is used to return a 401 unauthorized error to clients that try to access a protected resource without proper authentication.
 */
@Component
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
    /**
     * This method is called whenever an exception is thrown due to an unauthenticated user trying to access a protected resource.
     * @param request The request that was made.
     * @param response The response that will be sent.
     * @param authException The exception that was thrown.
     * @throws IOException If an input or output exception occurs.
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
    }
}
