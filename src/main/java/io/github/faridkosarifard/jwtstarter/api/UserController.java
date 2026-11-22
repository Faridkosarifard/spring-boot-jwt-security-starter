package io.github.faridkosarifard.jwtstarter.api;

import io.github.faridkosarifard.jwtstarter.api.dto.ChangePasswordRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.MessageResponse;
import io.github.faridkosarifard.jwtstarter.api.dto.UserResponse;
import io.github.faridkosarifard.jwtstarter.security.CurrentUser;
import io.github.faridkosarifard.jwtstarter.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@Tag(name = "Users")
public class UserController {

    private final AuthService authService;

    public UserController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Return the authenticated user profile")
    @GetMapping("/me")
    public UserResponse me(Authentication authentication) {
        return UserResponse.from(CurrentUser.account(authentication));
    }

    @Operation(summary = "Change the current user password and revoke existing sessions")
    @PostMapping("/me/password")
    public MessageResponse changePassword(
            Authentication authentication,
            @Valid @RequestBody ChangePasswordRequest request
    ) {
        authService.changePassword(CurrentUser.principal(authentication).getId(), request);
        return new MessageResponse("Password changed. Please sign in again.");
    }
}
