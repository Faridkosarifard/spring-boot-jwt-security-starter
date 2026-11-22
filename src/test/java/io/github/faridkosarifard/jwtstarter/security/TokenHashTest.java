package io.github.faridkosarifard.jwtstarter.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenHashTest {

    @Test
    void randomTokensAreUniqueAndHashIsStable() {
        String first = TokenHash.randomToken();
        String second = TokenHash.randomToken();
        assertThat(first).isNotEqualTo(second);
        assertThat(TokenHash.sha256(first)).isEqualTo(TokenHash.sha256(first));
        assertThat(TokenHash.sha256(first)).isNotEqualTo(TokenHash.sha256(second));
        assertThat(TokenHash.sha256(first)).hasSize(64);
    }
}
