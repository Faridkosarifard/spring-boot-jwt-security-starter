package io.github.faridkosarifard.jwtstarter.api.dto;

import jakarta.validation.constraints.Size;

import java.util.Set;

public record UpdateUserRequest(
        Boolean enabled,
        Boolean locked,
        @Size(min = 1) Set<String> roles
) {
}
