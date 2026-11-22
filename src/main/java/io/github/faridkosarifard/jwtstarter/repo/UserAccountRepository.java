package io.github.faridkosarifard.jwtstarter.repo;

import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByUsernameIgnoreCase(String username);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<UserAccount> findById(Long id);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<UserAccount> findByEmailIgnoreCase(String email);

    @EntityGraph(attributePaths = {"roles", "roles.permissions"})
    Optional<UserAccount> findByUsernameIgnoreCase(String username);

    default Optional<UserAccount> findByUsernameOrEmail(String identifier) {
        Optional<UserAccount> byUsername = findByUsernameIgnoreCase(identifier);
        if (byUsername.isPresent()) {
            return byUsername;
        }
        return findByEmailIgnoreCase(identifier);
    }
}
