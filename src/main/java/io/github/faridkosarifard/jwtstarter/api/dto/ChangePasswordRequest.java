package io.github.faridkosarifard.jwtstarter.api.dto;

import io.github.faridkosarifard.jwtstarter.validation.StrongPassword;
import jakarta.validation.constraints.NotBlank;

public record ChangePasswordRequest(
        @NotBlank String currentPassword,
        @StrongPassword String newPassword
) {
}
