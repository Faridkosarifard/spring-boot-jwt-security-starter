package io.github.faridkosarifard.jwtstarter.api.dto;

import io.github.faridkosarifard.jwtstarter.validation.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record RegisterRequest(
        @NotBlank @Size(min = 1, max = 100) String firstName,
        @NotBlank @Size(min = 1, max = 100) String lastName,
        @NotBlank
        @Size(min = 3, max = 50)
        @Pattern(regexp = "^[A-Za-z0-9._-]+$", message = "Username may contain letters, digits, dots, underscores, and hyphens")
        String username,
        @NotBlank @Email @Size(max = 255) String email,
        @StrongPassword String password,
        @Size(max = 32)
        @Pattern(regexp = "^$|^\\+?[0-9]{8,15}$", message = "Phone number must be 8-15 digits, optionally starting with +")
        String phoneNumber
) {
}
