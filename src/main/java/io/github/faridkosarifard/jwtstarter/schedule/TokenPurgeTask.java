package io.github.faridkosarifard.jwtstarter.schedule;

import io.github.faridkosarifard.jwtstarter.repo.EmailVerificationTokenRepository;
import io.github.faridkosarifard.jwtstarter.repo.PasswordResetTokenRepository;
import io.github.faridkosarifard.jwtstarter.repo.RefreshTokenRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Component
public class TokenPurgeTask {

    private static final Logger log = LoggerFactory.getLogger(TokenPurgeTask.class);

    private final RefreshTokenRepository refreshTokens;
    private final EmailVerificationTokenRepository verificationTokens;
    private final PasswordResetTokenRepository resetTokens;

    public TokenPurgeTask(
            RefreshTokenRepository refreshTokens,
            EmailVerificationTokenRepository verificationTokens,
            PasswordResetTokenRepository resetTokens
    ) {
        this.refreshTokens = refreshTokens;
        this.verificationTokens = verificationTokens;
        this.resetTokens = resetTokens;
    }

    @Scheduled(cron = "0 30 3 * * *")
    @Transactional
    public void purge() {
        Instant now = Instant.now();
        int refresh = refreshTokens.deleteExpired(now);
        int verification = verificationTokens.deleteUsedOrExpired(now);
        int reset = resetTokens.deleteUsedOrExpired(now);
        log.info("Purged expired tokens: refresh={}, verification={}, reset={}", refresh, verification, reset);
    }
}
