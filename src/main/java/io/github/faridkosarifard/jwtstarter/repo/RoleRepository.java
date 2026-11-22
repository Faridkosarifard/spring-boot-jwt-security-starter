package io.github.faridkosarifard.jwtstarter.repo;

import io.github.faridkosarifard.jwtstarter.domain.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

public interface RoleRepository extends JpaRepository<Role, Long> {

    @EntityGraph(attributePaths = "permissions")
    Optional<Role> findByName(String name);

    @EntityGraph(attributePaths = "permissions")
    Set<Role> findByNameIn(Collection<String> names);
}
