package io.github.faridkosarifard.jwtstarter.security;

import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import io.github.faridkosarifard.jwtstarter.domain.Role;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class JwtServiceTest {

    private static final String SECRET = "test-only-jwt-signing-key-not-for-production-use-32b";

    private JwtService jwtService;
    private UserAccount user;

    @BeforeEach
    void setUp() throws Exception {
        jwtService = new JwtService(properties(Duration.ofMinutes(15)));
        user = UserAccount.newAccount("ada@example.com", "ada", "hash", "Ada", "Lovelace", null);
        Field id = UserAccount.class.getDeclaredField("id");
        id.setAccessible(true);
        id.set(user, 42L);
        Role role = new Role(Role.USER);
        user.setRoles(Set.of(role));
    }

    @Test
    void accessTokenContainsExpectedClaims() {
        String token = jwtService.createAccessToken(user);
        var claims = jwtService.parseAccessToken(token);
        assertThat(claims.getSubject()).isEqualTo("42");
        assertThat(claims.get("username", String.class)).isEqualTo("ada");
        assertThat(claims.get("email", String.class)).isEqualTo("ada@example.com");
        assertThat(claims.get("typ", String.class)).isEqualTo("access");
        assertThat(claims.getIssuer()).isEqualTo("jwt-security-starter-test");
        assertThat(jwtService.accessTokenTtlSeconds()).isEqualTo(900);
    }

    @Test
    void rejectsExpiredAndWrongTypeTokens() {
        JwtService shortLived = new JwtService(properties(Duration.ofSeconds(-2)));
        String expired = shortLived.createAccessToken(user);
        assertThatThrownBy(() -> jwtService.parseAccessToken(expired)).isInstanceOf(ExpiredJwtException.class);

        String otherType = Jwts.builder()
                .issuer("jwt-security-starter-test")
                .subject("42")
                .claim("typ", "refresh")
                .expiration(new Date(System.currentTimeMillis() + 60_000))
                .signWith(io.jsonwebtoken.security.Keys.hmacShaKeyFor(JwtService.decodeSecret(SECRET)))
                .compact();
        assertThatThrownBy(() -> jwtService.parseAccessToken(otherType)).isInstanceOf(JwtException.class);
    }

    @Test
    void rejectsInvalidOrShortSecrets() {
        assertThatThrownBy(() -> JwtService.decodeSecret("too-short"))
                .isInstanceOf(IllegalStateException.class)
                .hasMessageContaining("32 bytes");
        byte[] decoded = JwtService.decodeSecret(SECRET);
        assertThat(decoded.length).isGreaterThanOrEqualTo(32);
    }

    private static AppProperties properties(Duration accessTtl) {
        return new AppProperties(
                new AppProperties.Jwt(SECRET, "jwt-security-starter-test", accessTtl, Duration.ofDays(14)),
                new AppProperties.Cors(List.of("http://localhost:3000"), List.of("GET"), List.of("Authorization"), true),
                new AppProperties.Security(5, Duration.ofMinutes(15), 20, Duration.ofMinutes(15), Duration.ofHours(24), Duration.ofHours(1)),
                new AppProperties.Mail("logging", "noreply@localhost", "http://localhost:8080"),
                new AppProperties.Admin("", "", "", "Admin", "User")
        );
    }
}
