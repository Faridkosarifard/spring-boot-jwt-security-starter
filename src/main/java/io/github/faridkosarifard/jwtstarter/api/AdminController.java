package io.github.faridkosarifard.jwtstarter.api;

import io.github.faridkosarifard.jwtstarter.api.dto.UpdateUserRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.UserResponse;
import io.github.faridkosarifard.jwtstarter.service.UserAdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@PreAuthorize("hasRole('ADMIN')")
@Tag(name = "Administration")
public class AdminController {

    private final UserAdminService userAdminService;

    public AdminController(UserAdminService userAdminService) {
        this.userAdminService = userAdminService;
    }

    @Operation(summary = "List registered users")
    @GetMapping("/users")
    @PreAuthorize("hasAuthority('admin:read')")
    public Page<UserResponse> list(@PageableDefault(size = 20) Pageable pageable) {
        return userAdminService.list(pageable);
    }

    @Operation(summary = "Get a user by id")
    @GetMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:read')")
    public UserResponse get(@PathVariable Long id) {
        return userAdminService.get(id);
    }

    @Operation(summary = "Update a user's enabled flag, lock state, or roles")
    @PatchMapping("/users/{id}")
    @PreAuthorize("hasAuthority('admin:write')")
    public UserResponse update(@PathVariable Long id, @Valid @RequestBody UpdateUserRequest request) {
        return userAdminService.update(id, request);
    }

    @Operation(summary = "Clear a user's lock and failed sign-in counter")
    @PostMapping("/users/{id}/unlock")
    @PreAuthorize("hasAuthority('admin:write')")
    public UserResponse unlock(@PathVariable Long id) {
        return userAdminService.unlock(id);
    }
}
