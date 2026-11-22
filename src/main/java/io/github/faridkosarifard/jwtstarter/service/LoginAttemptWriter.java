package io.github.faridkosarifard.jwtstarter.service;

import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.github.faridkosarifard.jwtstarter.repo.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginAttemptWriter {

    private final UserAccountRepository users;
    private final AppProperties properties;

    public LoginAttemptWriter(UserAccountRepository users, AppProperties properties) {
        this.users = users;
        this.properties = properties;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public boolean recordFailure(Long userId) {
        UserAccount user = users.findById(userId).orElseThrow();
        user.registerFailedLogin(properties.security().maxFailedLogins(), properties.security().lockoutDuration());
        users.save(user);
        return user.isLockedNow();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void recordSuccess(Long userId) {
        UserAccount user = users.findById(userId).orElseThrow();
        user.registerSuccessfulLogin();
        users.save(user);
    }
}
