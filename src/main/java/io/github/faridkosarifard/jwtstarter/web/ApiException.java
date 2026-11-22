package io.github.faridkosarifard.jwtstarter.web;

import org.springframework.http.HttpStatus;

import java.net.URI;

public class ApiException extends RuntimeException {

    private final HttpStatus status;
    private final String title;
    private final URI type;

    public ApiException(HttpStatus status, String title, URI type, String message) {
        super(message);
        this.status = status;
        this.title = title;
        this.type = type;
    }

    public static ApiException unauthorized(String message) {
        return new ApiException(HttpStatus.UNAUTHORIZED, "Unauthorized", ProblemTypes.UNAUTHORIZED, message);
    }

    public static ApiException forbidden(String message) {
        return new ApiException(HttpStatus.FORBIDDEN, "Forbidden", ProblemTypes.FORBIDDEN, message);
    }

    public static ApiException conflict(String message) {
        return new ApiException(HttpStatus.CONFLICT, "Conflict", ProblemTypes.CONFLICT, message);
    }

    public static ApiException notFound(String message) {
        return new ApiException(HttpStatus.NOT_FOUND, "Not Found", ProblemTypes.NOT_FOUND, message);
    }

    public static ApiException locked(String message) {
        return new ApiException(HttpStatus.LOCKED, "Account Locked", ProblemTypes.LOCKED, message);
    }

    public static ApiException rateLimited(String message) {
        return new ApiException(HttpStatus.TOO_MANY_REQUESTS, "Too Many Requests", ProblemTypes.RATE_LIMITED, message);
    }

    public static ApiException badRequest(String message) {
        return new ApiException(HttpStatus.BAD_REQUEST, "Bad Request", ProblemTypes.BAD_REQUEST, message);
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getTitle() {
        return title;
    }

    public URI getType() {
        return type;
    }
}
