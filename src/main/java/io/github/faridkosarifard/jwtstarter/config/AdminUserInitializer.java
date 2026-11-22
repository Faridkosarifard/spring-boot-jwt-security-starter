package io.github.faridkosarifard.jwtstarter.config;

import io.github.faridkosarifard.jwtstarter.domain.Role;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.github.faridkosarifard.jwtstarter.repo.RoleRepository;
import io.github.faridkosarifard.jwtstarter.repo.UserAccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Component
public class AdminUserInitializer implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminUserInitializer.class);

    private final AppProperties properties;
    private final UserAccountRepository users;
    private final RoleRepository roles;
    private final PasswordEncoder passwordEncoder;

    public AdminUserInitializer(
            AppProperties properties,
            UserAccountRepository users,
            RoleRepository roles,
            PasswordEncoder passwordEncoder
    ) {
        this.properties = properties;
        this.users = users;
        this.roles = roles;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        AppProperties.Admin admin = properties.admin();
        if (!admin.isConfigured()) {
            log.info("No bootstrap admin configured; skipping admin user creation");
            return;
        }
        if (users.existsByEmailIgnoreCase(admin.email()) || users.existsByUsernameIgnoreCase(admin.username())) {
            log.info("Bootstrap admin already exists; skipping creation");
            return;
        }
        UserAccount account = UserAccount.newAccount(
                admin.email().trim().toLowerCase(),
                admin.username().trim(),
                passwordEncoder.encode(admin.password()),
                blankToDefault(admin.firstName(), "Admin"),
                blankToDefault(admin.lastName(), "User"),
                null
        );
        account.setEnabled(true);
        Set<Role> assigned = new HashSet<>(roles.findByNameIn(Set.of(Role.ADMIN, Role.USER)));
        if (assigned.size() < 2) {
            throw new IllegalStateException("Required roles were not seeded by Flyway");
        }
        account.setRoles(assigned);
        users.save(account);
        log.info("Created bootstrap admin user '{}'", account.getUsername());
    }

    private static String blankToDefault(String value, String fallback) {
        return value == null || value.isBlank() ? fallback : value;
    }
}
