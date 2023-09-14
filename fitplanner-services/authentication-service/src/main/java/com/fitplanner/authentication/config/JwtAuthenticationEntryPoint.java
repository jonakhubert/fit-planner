package com.fitplanner.authentication.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fitplanner.authentication.model.api.ApiError;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence( // if auth request is invalid (/api/auth/**) custom response is returned
        HttpServletRequest request,
        HttpServletResponse response,
        AuthenticationException authException
    ) throws IOException {
        var apiError = new ApiError(
            request.getRequestURI(),
            authException.getMessage(),
            HttpStatus.UNAUTHORIZED.value(),
            LocalDateTime.now().toString()
        );

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
        var objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(apiError));
    }
}
