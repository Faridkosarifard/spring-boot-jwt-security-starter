package io.github.faridkosarifard.jwtstarter.security;

import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtService {

    public static final String TOKEN_TYPE_ACCESS = "access";

    private final AppProperties properties;
    private SecretKey key;

    public JwtService(AppProperties properties) {
        this.properties = properties;
        this.key = Keys.hmacShaKeyFor(decodeSecret(properties.jwt().secret()));
    }

    public String createAccessToken(UserAccount user) {
        Instant now = Instant.now();
        Instant expires = now.plus(properties.jwt().accessTokenTtl());
        return Jwts.builder()
                .issuer(properties.jwt().issuer())
                .subject(String.valueOf(user.getId()))
                .claim("username", user.getUsername())
                .claim("email", user.getEmail())
                .claim("roles", user.roleNames())
                .claim("authorities", user.permissionNames())
                .claim("typ", TOKEN_TYPE_ACCESS)
                .id(UUID.randomUUID().toString())
                .issuedAt(Date.from(now))
                .expiration(Date.from(expires))
                .signWith(key)
                .compact();
    }

    public long accessTokenTtlSeconds() {
        return properties.jwt().accessTokenTtl().toSeconds();
    }

    public Claims parseAccessToken(String token) {
        Claims claims = Jwts.parser()
                .verifyWith(key)
                .requireIssuer(properties.jwt().issuer())
                .build()
                .parseSignedClaims(token)
                .getPayload();
        Object typ = claims.get("typ");
        if (!TOKEN_TYPE_ACCESS.equals(typ)) {
            throw new JwtException("Access token required");
        }
        return claims;
    }

    static byte[] decodeSecret(String secret) {
        byte[] bytes;
        try {
            bytes = Base64.getDecoder().decode(secret);
            if (bytes.length < 32) {
                bytes = secret.getBytes(StandardCharsets.UTF_8);
            }
        } catch (IllegalArgumentException ex) {
            bytes = secret.getBytes(StandardCharsets.UTF_8);
        }
        if (bytes.length < 32) {
            throw new IllegalStateException(
                    "app.jwt.secret must be at least 32 bytes. Generate one with: openssl rand -base64 48");
        }
        return bytes;
    }
}
