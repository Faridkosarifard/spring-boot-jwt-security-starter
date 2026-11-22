package io.github.faridkosarifard.jwtstarter.repo;

import io.github.faridkosarifard.jwtstarter.domain.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByName(String name);
}
