package io.github.faridkosarifard.jwtstarter.security;

import tools.jackson.databind.json.JsonMapper;
import io.github.faridkosarifard.jwtstarter.web.ProblemTypes;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JsonMapper objectMapper;

    public RestAuthenticationEntryPoint(JsonMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException
    ) throws IOException {
        ProblemDetail detail = ProblemDetail.forStatusAndDetail(
                HttpStatus.UNAUTHORIZED, "Authentication is required to access this resource");
        detail.setTitle("Unauthorized");
        detail.setType(ProblemTypes.UNAUTHORIZED);
        detail.setProperty("path", request.getRequestURI());
        write(response, HttpStatus.UNAUTHORIZED, detail);
    }

    private void write(HttpServletResponse response, HttpStatus status, ProblemDetail detail) throws IOException {
        response.setStatus(status.value());
        response.setContentType(MediaType.APPLICATION_PROBLEM_JSON_VALUE);
        objectMapper.writeValue(response.getOutputStream(), detail);
    }
}
