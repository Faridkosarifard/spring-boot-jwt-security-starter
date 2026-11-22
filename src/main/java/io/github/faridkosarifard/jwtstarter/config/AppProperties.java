package io.github.faridkosarifard.jwtstarter.config;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

import java.time.Duration;
import java.util.List;

@Validated
@ConfigurationProperties(prefix = "app")
public record AppProperties(
        @Valid Jwt jwt,
        @Valid Cors cors,
        @Valid Security security,
        @Valid Mail mail,
        @Valid Admin admin
) {

    public record Jwt(
            @NotBlank String secret,
            @NotBlank String issuer,
            Duration accessTokenTtl,
            Duration refreshTokenTtl
    ) {
    }

    public record Cors(
            @NotEmpty List<String> allowedOrigins,
            @NotEmpty List<String> allowedMethods,
            @NotEmpty List<String> allowedHeaders,
            boolean allowCredentials
    ) {
    }

    public record Security(
            @Positive int maxFailedLogins,
            Duration lockoutDuration,
            @Positive int loginRateLimit,
            Duration loginRateWindow,
            Duration emailVerificationTtl,
            Duration passwordResetTtl
    ) {
    }

    public record Mail(
            @NotBlank String mode,
            @NotBlank String from,
            @NotBlank String baseUrl
    ) {
        public boolean smtpEnabled() {
            return "smtp".equalsIgnoreCase(mode);
        }
    }

    public record Admin(
            String email,
            String username,
            String password,
            String firstName,
            String lastName
    ) {
        public boolean isConfigured() {
            return notBlank(email) && notBlank(username) && notBlank(password);
        }

        private static boolean notBlank(String value) {
            return value != null && !value.isBlank();
        }
    }
}
