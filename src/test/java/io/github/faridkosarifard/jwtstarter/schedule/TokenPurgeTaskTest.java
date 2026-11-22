package io.github.faridkosarifard.jwtstarter.schedule;

import io.github.faridkosarifard.jwtstarter.repo.EmailVerificationTokenRepository;
import io.github.faridkosarifard.jwtstarter.repo.PasswordResetTokenRepository;
import io.github.faridkosarifard.jwtstarter.repo.RefreshTokenRepository;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TokenPurgeTaskTest {

    @Test
    void purgeDelegatesToRepositories() {
        RefreshTokenRepository refresh = mock(RefreshTokenRepository.class);
        EmailVerificationTokenRepository verification = mock(EmailVerificationTokenRepository.class);
        PasswordResetTokenRepository reset = mock(PasswordResetTokenRepository.class);
        when(refresh.deleteExpired(any(Instant.class))).thenReturn(1);
        when(verification.deleteUsedOrExpired(any(Instant.class))).thenReturn(2);
        when(reset.deleteUsedOrExpired(any(Instant.class))).thenReturn(3);
        new TokenPurgeTask(refresh, verification, reset).purge();
        verify(refresh).deleteExpired(any(Instant.class));
        verify(verification).deleteUsedOrExpired(any(Instant.class));
        verify(reset).deleteUsedOrExpired(any(Instant.class));
    }
}
