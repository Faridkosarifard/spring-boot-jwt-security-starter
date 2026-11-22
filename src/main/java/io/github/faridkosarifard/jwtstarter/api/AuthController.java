package io.github.faridkosarifard.jwtstarter.api;

import io.github.faridkosarifard.jwtstarter.api.dto.ForgotPasswordRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.LoginRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.LogoutRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.MessageResponse;
import io.github.faridkosarifard.jwtstarter.api.dto.RefreshRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.RegisterRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.ResendVerificationRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.ResetPasswordRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.TokenResponse;
import io.github.faridkosarifard.jwtstarter.security.CurrentUser;
import io.github.faridkosarifard.jwtstarter.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@Tag(name = "Authentication")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(summary = "Register a new user and send a verification email")
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse register(@Valid @RequestBody RegisterRequest request, HttpServletRequest http) {
        authService.register(request, clientIp(http));
        return new MessageResponse("Registration successful. Please verify your email address to sign in.");
    }

    @Operation(summary = "Confirm an email address with a one-time token")
    @GetMapping("/verify-email")
    public MessageResponse verifyEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return new MessageResponse("Email address verified. You can now sign in.");
    }

    @Operation(summary = "Resend the email verification link")
    @PostMapping("/resend-verification")
    public MessageResponse resendVerification(
            @Valid @RequestBody ResendVerificationRequest request,
            HttpServletRequest http
    ) {
        authService.resendVerification(request, clientIp(http));
        return new MessageResponse(AuthService.GENERIC_VERIFICATION_MESSAGE);
    }

    @Operation(summary = "Sign in with username or email and receive access and refresh tokens")
    @PostMapping("/login")
    public TokenResponse login(@Valid @RequestBody LoginRequest request, HttpServletRequest http) {
        return authService.login(request, clientIp(http));
    }

    @Operation(summary = "Rotate a refresh token and issue a new token pair")
    @PostMapping("/refresh")
    public TokenResponse refresh(@Valid @RequestBody RefreshRequest request, HttpServletRequest http) {
        return authService.refresh(request.refreshToken(), clientIp(http));
    }

    @Operation(summary = "Revoke a refresh token")
    @PostMapping("/logout")
    public MessageResponse logout(@Valid @RequestBody LogoutRequest request) {
        authService.logout(request.refreshToken());
        return new MessageResponse("Signed out");
    }

    @Operation(summary = "Revoke every refresh token for the current user")
    @PostMapping("/logout-all")
    public MessageResponse logoutAll(Authentication authentication) {
        authService.logoutAll(CurrentUser.account(authentication));
        return new MessageResponse("All sessions have been signed out");
    }

    @Operation(summary = "Request a password reset email")
    @PostMapping("/forgot-password")
    public MessageResponse forgotPassword(
            @Valid @RequestBody ForgotPasswordRequest request,
            HttpServletRequest http
    ) {
        authService.forgotPassword(request, clientIp(http));
        return new MessageResponse(AuthService.GENERIC_RESET_MESSAGE);
    }

    @Operation(summary = "Reset a password with a one-time token")
    @PostMapping("/reset-password")
    public MessageResponse resetPassword(@Valid @RequestBody ResetPasswordRequest request) {
        authService.resetPassword(request);
        return new MessageResponse("Password has been reset. You can now sign in.");
    }

    static String clientIp(HttpServletRequest request) {
        return request.getRemoteAddr() == null ? "unknown" : request.getRemoteAddr();
    }
}
