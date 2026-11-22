package io.github.faridkosarifard.jwtstarter.api.dto;

import io.github.faridkosarifard.jwtstarter.domain.UserAccount;

import java.time.Instant;
import java.util.Set;
import java.util.TreeSet;

public record UserResponse(
        Long id,
        String email,
        String username,
        String firstName,
        String lastName,
        String phoneNumber,
        boolean enabled,
        boolean locked,
        Set<String> roles,
        Instant createdAt
) {
    public static UserResponse from(UserAccount user) {
        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getPhoneNumber(),
                user.isEnabled(),
                user.isLockedNow(),
                new TreeSet<>(user.roleNames()),
                user.getCreatedAt()
        );
    }
}
