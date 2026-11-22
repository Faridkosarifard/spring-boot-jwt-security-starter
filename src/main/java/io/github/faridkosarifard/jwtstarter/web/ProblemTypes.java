package io.github.faridkosarifard.jwtstarter.web;

import java.net.URI;

public final class ProblemTypes {

    private static final String BASE =
            "https://github.com/Faridkosarifard/spring-boot-jwt-security-starter/problems/";

    public static final URI UNAUTHORIZED = URI.create(BASE + "unauthorized");
    public static final URI FORBIDDEN = URI.create(BASE + "forbidden");
    public static final URI VALIDATION = URI.create(BASE + "validation");
    public static final URI CONFLICT = URI.create(BASE + "conflict");
    public static final URI NOT_FOUND = URI.create(BASE + "not-found");
    public static final URI LOCKED = URI.create(BASE + "account-locked");
    public static final URI RATE_LIMITED = URI.create(BASE + "rate-limited");
    public static final URI BAD_REQUEST = URI.create(BASE + "bad-request");

    private ProblemTypes() {
    }
}
