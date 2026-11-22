package io.github.faridkosarifard.jwtstarter.security;

import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.github.faridkosarifard.jwtstarter.web.ApiException;
import org.springframework.security.core.Authentication;

public final class CurrentUser {

    private CurrentUser() {
    }

    public static UserPrincipal principal(Authentication authentication) {
        if (authentication == null || !(authentication.getPrincipal() instanceof UserPrincipal principal)) {
            throw ApiException.unauthorized("Authentication is required");
        }
        return principal;
    }

    public static UserAccount account(Authentication authentication) {
        return principal(authentication).getUser();
    }
}
