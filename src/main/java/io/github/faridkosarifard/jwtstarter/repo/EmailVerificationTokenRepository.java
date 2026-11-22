package io.github.faridkosarifard.jwtstarter.repo;

import io.github.faridkosarifard.jwtstarter.domain.EmailVerificationToken;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.Optional;

public interface EmailVerificationTokenRepository extends JpaRepository<EmailVerificationToken, Long> {

    @EntityGraph(attributePaths = "user")
    Optional<EmailVerificationToken> findByTokenHash(String tokenHash);

    @Modifying
    @Query("update EmailVerificationToken t set t.usedAt = :now where t.user = :user and t.usedAt is null")
    int expireOutstanding(@Param("user") UserAccount user, @Param("now") Instant now);

    @Modifying
    @Query("delete from EmailVerificationToken t where t.expiresAt < :cutoff or t.usedAt is not null")
    int deleteUsedOrExpired(@Param("cutoff") Instant cutoff);
}
