package io.github.faridkosarifard.jwtstarter.security;

import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class RateLimitServiceTest {

    @Test
    void blocksAfterConfiguredNumberOfAttempts() {
        AppProperties properties = new AppProperties(
                new AppProperties.Jwt("test-only-jwt-signing-key-not-for-production-use-32b", "test", Duration.ofMinutes(15), Duration.ofDays(14)),
                new AppProperties.Cors(List.of("http://localhost:3000"), List.of("GET"), List.of("*"), false),
                new AppProperties.Security(5, Duration.ofMinutes(15), 3, Duration.ofMinutes(15), Duration.ofHours(24), Duration.ofHours(1)),
                new AppProperties.Mail("logging", "noreply@localhost", "http://localhost:8080"),
                new AppProperties.Admin("", "", "", "Admin", "User")
        );
        RateLimitService service = new RateLimitService(properties);
        assertThat(service.allow("ip")).isTrue();
        assertThat(service.allow("ip")).isTrue();
        assertThat(service.allow("ip")).isTrue();
        assertThat(service.allow("ip")).isFalse();
        assertThat(service.allow("other")).isTrue();
        service.clear();
        assertThat(service.allow("ip")).isTrue();
    }
}
