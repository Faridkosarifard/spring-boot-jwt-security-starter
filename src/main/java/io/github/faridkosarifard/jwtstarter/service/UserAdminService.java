package io.github.faridkosarifard.jwtstarter.service;

import io.github.faridkosarifard.jwtstarter.api.dto.UpdateUserRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.UserResponse;
import io.github.faridkosarifard.jwtstarter.domain.Role;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.github.faridkosarifard.jwtstarter.repo.RoleRepository;
import io.github.faridkosarifard.jwtstarter.repo.UserAccountRepository;
import io.github.faridkosarifard.jwtstarter.web.ApiException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserAdminService {

    private final UserAccountRepository users;
    private final RoleRepository roles;
    private final TokenService tokenService;

    public UserAdminService(UserAccountRepository users, RoleRepository roles, TokenService tokenService) {
        this.users = users;
        this.roles = roles;
        this.tokenService = tokenService;
    }

    @Transactional(readOnly = true)
    public Page<UserResponse> list(Pageable pageable) {
        return users.findAll(pageable).map(UserResponse::from);
    }

    @Transactional(readOnly = true)
    public UserResponse get(Long id) {
        return UserResponse.from(requireUser(id));
    }

    @Transactional
    public UserResponse update(Long id, UpdateUserRequest request) {
        UserAccount user = requireUser(id);
        if (request.enabled() != null) {
            user.setEnabled(request.enabled());
        }
        if (Boolean.TRUE.equals(request.locked())) {
            user.lockPermanently();
        } else if (Boolean.FALSE.equals(request.locked())) {
            user.unlock();
        }
        if (request.roles() != null) {
            Set<String> names = new HashSet<>();
            for (String roleName : request.roles()) {
                names.add(normalizeRole(roleName));
            }
            Set<Role> assigned = roles.findByNameIn(names);
            if (assigned.size() != names.size()) {
                throw ApiException.badRequest("One or more roles are unknown");
            }
            user.setRoles(assigned);
        }
        if (!user.isEnabled() || user.isLockedNow()) {
            tokenService.revokeAll(user);
        }
        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse unlock(Long id) {
        UserAccount user = requireUser(id);
        user.unlock();
        return UserResponse.from(user);
    }

    private UserAccount requireUser(Long id) {
        return users.findById(id)
                .orElseThrow(() -> ApiException.notFound("User not found"));
    }

    private static String normalizeRole(String roleName) {
        String value = roleName.trim().toUpperCase();
        return value.startsWith("ROLE_") ? value : "ROLE_" + value;
    }
}
