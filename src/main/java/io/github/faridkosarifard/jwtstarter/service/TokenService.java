package io.github.faridkosarifard.jwtstarter.service;

import io.github.faridkosarifard.jwtstarter.api.dto.TokenResponse;
import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import io.github.faridkosarifard.jwtstarter.domain.RefreshToken;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.github.faridkosarifard.jwtstarter.repo.RefreshTokenRepository;
import io.github.faridkosarifard.jwtstarter.security.JwtService;
import io.github.faridkosarifard.jwtstarter.security.TokenHash;
import io.github.faridkosarifard.jwtstarter.web.ApiException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class TokenService {

    private final JwtService jwtService;
    private final RefreshTokenRepository refreshTokens;
    private final AppProperties properties;

    public TokenService(JwtService jwtService, RefreshTokenRepository refreshTokens, AppProperties properties) {
        this.jwtService = jwtService;
        this.refreshTokens = refreshTokens;
        this.properties = properties;
    }

    public TokenResponse issueTokens(UserAccount user) {
        return issueTokens(user, UUID.randomUUID().toString());
    }

    public TokenResponse issueTokens(UserAccount user, String familyId) {
        String accessToken = jwtService.createAccessToken(user);
        String rawRefresh = TokenHash.randomToken();
        RefreshToken stored = new RefreshToken(
                user,
                TokenHash.sha256(rawRefresh),
                familyId,
                Instant.now().plus(properties.jwt().refreshTokenTtl())
        );
        refreshTokens.save(stored);
        return TokenResponse.bearer(accessToken, rawRefresh, jwtService.accessTokenTtlSeconds());
    }

    @Transactional
    public TokenResponse rotate(String rawRefreshToken) {
        String hash = TokenHash.sha256(rawRefreshToken);
        RefreshToken current = refreshTokens.findByTokenHash(hash)
                .orElseThrow(() -> ApiException.unauthorized("Invalid refresh token"));
        if (current.isRevoked()) {
            refreshTokens.revokeFamily(current.getFamilyId());
            throw ApiException.unauthorized("Refresh token reuse detected; the token family has been revoked");
        }
        if (!current.isActive()) {
            current.revoke();
            throw ApiException.unauthorized("Refresh token has expired");
        }
        UserAccount user = current.getUser();
        if (!user.isEnabled() || user.isLockedNow()) {
            current.revoke();
            throw ApiException.unauthorized("Account is not allowed to refresh a session");
        }
        current.revoke();
        TokenResponse issued = issueTokens(user, current.getFamilyId());
        RefreshToken replacement = refreshTokens.findByTokenHash(TokenHash.sha256(issued.refreshToken()))
                .orElseThrow(() -> new IllegalStateException("Replacement refresh token was not stored"));
        current.setReplacedBy(replacement);
        return issued;
    }

    @Transactional
    public void revoke(String rawRefreshToken) {
        refreshTokens.findByTokenHash(TokenHash.sha256(rawRefreshToken))
                .ifPresent(RefreshToken::revoke);
    }

    @Transactional
    public void revokeAll(UserAccount user) {
        refreshTokens.revokeAllForUser(user);
    }
}
