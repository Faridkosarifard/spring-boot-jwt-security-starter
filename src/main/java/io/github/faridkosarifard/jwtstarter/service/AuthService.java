package io.github.faridkosarifard.jwtstarter.service;

import io.github.faridkosarifard.jwtstarter.api.dto.ChangePasswordRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.ForgotPasswordRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.LoginRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.RegisterRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.ResendVerificationRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.ResetPasswordRequest;
import io.github.faridkosarifard.jwtstarter.api.dto.TokenResponse;
import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import io.github.faridkosarifard.jwtstarter.domain.EmailVerificationToken;
import io.github.faridkosarifard.jwtstarter.domain.PasswordResetToken;
import io.github.faridkosarifard.jwtstarter.domain.Role;
import io.github.faridkosarifard.jwtstarter.domain.UserAccount;
import io.github.faridkosarifard.jwtstarter.notification.EmailTemplates;
import io.github.faridkosarifard.jwtstarter.notification.NotificationMailSender;
import io.github.faridkosarifard.jwtstarter.notification.SmsSender;
import io.github.faridkosarifard.jwtstarter.repo.EmailVerificationTokenRepository;
import io.github.faridkosarifard.jwtstarter.repo.PasswordResetTokenRepository;
import io.github.faridkosarifard.jwtstarter.repo.RoleRepository;
import io.github.faridkosarifard.jwtstarter.repo.UserAccountRepository;
import io.github.faridkosarifard.jwtstarter.security.RateLimitService;
import io.github.faridkosarifard.jwtstarter.security.TokenHash;
import io.github.faridkosarifard.jwtstarter.web.ApiException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Locale;
import java.util.Set;

@Service
public class AuthService {

    public static final String GENERIC_RESET_MESSAGE =
            "If an account exists for that email, password reset instructions have been sent.";
    public static final String GENERIC_VERIFICATION_MESSAGE =
            "If the account needs verification, a new email has been sent.";

    private final UserAccountRepository users;
    private final RoleRepository roles;
    private final EmailVerificationTokenRepository verificationTokens;
    private final PasswordResetTokenRepository resetTokens;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;
    private final NotificationMailSender mailSender;
    private final SmsSender smsSender;
    private final EmailTemplates templates;
    private final RateLimitService rateLimitService;
    private final LoginAttemptWriter loginAttemptWriter;
    private final AppProperties properties;

    public AuthService(
            UserAccountRepository users,
            RoleRepository roles,
            EmailVerificationTokenRepository verificationTokens,
            PasswordResetTokenRepository resetTokens,
            PasswordEncoder passwordEncoder,
            TokenService tokenService,
            NotificationMailSender mailSender,
            SmsSender smsSender,
            EmailTemplates templates,
            RateLimitService rateLimitService,
            LoginAttemptWriter loginAttemptWriter,
            AppProperties properties
    ) {
        this.users = users;
        this.roles = roles;
        this.verificationTokens = verificationTokens;
        this.resetTokens = resetTokens;
        this.passwordEncoder = passwordEncoder;
        this.tokenService = tokenService;
        this.mailSender = mailSender;
        this.smsSender = smsSender;
        this.templates = templates;
        this.rateLimitService = rateLimitService;
        this.loginAttemptWriter = loginAttemptWriter;
        this.properties = properties;
    }

    @Transactional
    public void register(RegisterRequest request, String clientIp) {
        requireRateLimit("register:" + clientIp);
        String email = request.email().trim().toLowerCase(Locale.ROOT);
        String username = request.username().trim();
        if (users.existsByEmailIgnoreCase(email)) {
            throw ApiException.conflict("An account with this email already exists");
        }
        if (users.existsByUsernameIgnoreCase(username)) {
            throw ApiException.conflict("An account with this username already exists");
        }
        Role userRole = roles.findByName(Role.USER)
                .orElseThrow(() -> new IllegalStateException("ROLE_USER was not seeded"));
        String phone = blankToNull(request.phoneNumber());
        UserAccount account = UserAccount.newAccount(
                email,
                username,
                passwordEncoder.encode(request.password()),
                request.firstName().trim(),
                request.lastName().trim(),
                phone
        );
        account.setRoles(Set.of(userRole));
        users.save(account);
        issueVerificationEmail(account);
    }

    @Transactional
    public void verifyEmail(String rawToken) {
        EmailVerificationToken token = verificationTokens.findByTokenHash(TokenHash.sha256(rawToken))
                .orElseThrow(() -> ApiException.badRequest("Invalid or expired verification token"));
        if (!token.isUsable()) {
            throw ApiException.badRequest("Invalid or expired verification token");
        }
        token.markUsed();
        token.getUser().setEnabled(true);
    }

    @Transactional
    public void resendVerification(ResendVerificationRequest request, String clientIp) {
        requireRateLimit("verify:" + clientIp);
        users.findByEmailIgnoreCase(request.email().trim().toLowerCase(Locale.ROOT))
                .filter(user -> !user.isEnabled())
                .ifPresent(this::issueVerificationEmail);
    }

    @Transactional
    public TokenResponse login(LoginRequest request, String clientIp) {
        requireRateLimit("login:" + clientIp);
        String identifier = request.username().trim();
        UserAccount user = users.findByUsernameOrEmail(identifier).orElse(null);
        if (user == null) {
            throw ApiException.unauthorized("Invalid username or password");
        }
        if (user.isLockedNow()) {
            throw ApiException.locked("This account is temporarily locked after too many failed sign-in attempts");
        }
        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            if (loginAttemptWriter.recordFailure(user.getId())) {
                throw ApiException.locked("This account is temporarily locked after too many failed sign-in attempts");
            }
            throw ApiException.unauthorized("Invalid username or password");
        }
        if (!user.isEnabled()) {
            throw ApiException.forbidden("Please verify your email address before signing in");
        }
        loginAttemptWriter.recordSuccess(user.getId());
        return tokenService.issueTokens(users.findById(user.getId()).orElse(user));
    }

    @Transactional
    public TokenResponse refresh(String refreshToken, String clientIp) {
        requireRateLimit("refresh:" + clientIp);
        return tokenService.rotate(refreshToken);
    }

    @Transactional
    public void logout(String refreshToken) {
        tokenService.revoke(refreshToken);
    }

    @Transactional
    public void logoutAll(UserAccount user) {
        tokenService.revokeAll(user);
    }

    @Transactional
    public void forgotPassword(ForgotPasswordRequest request, String clientIp) {
        requireRateLimit("forgot:" + clientIp);
        users.findByEmailIgnoreCase(request.email().trim().toLowerCase(Locale.ROOT))
                .ifPresent(this::issuePasswordReset);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest request) {
        PasswordResetToken token = resetTokens.findByTokenHash(TokenHash.sha256(request.token()))
                .orElseThrow(() -> ApiException.badRequest("Invalid or expired password reset token"));
        if (!token.isUsable()) {
            throw ApiException.badRequest("Invalid or expired password reset token");
        }
        token.markUsed();
        UserAccount user = token.getUser();
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        user.unlock();
        tokenService.revokeAll(user);
        resetTokens.expireOutstanding(user, Instant.now());
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest request) {
        UserAccount user = users.findById(userId)
                .orElseThrow(() -> ApiException.unauthorized("Authentication is required"));
        if (!passwordEncoder.matches(request.currentPassword(), user.getPasswordHash())) {
            throw ApiException.unauthorized("Current password is incorrect");
        }
        user.setPasswordHash(passwordEncoder.encode(request.newPassword()));
        tokenService.revokeAll(user);
    }

    private void issueVerificationEmail(UserAccount user) {
        verificationTokens.expireOutstanding(user, Instant.now());
        String raw = TokenHash.randomToken();
        verificationTokens.save(new EmailVerificationToken(
                user,
                TokenHash.sha256(raw),
                Instant.now().plus(properties.security().emailVerificationTtl())
        ));
        mailSender.send(
                user.getEmail(),
                templates.verificationSubject(),
                templates.verificationBody(user.getFirstName(), raw)
        );
    }

    private void issuePasswordReset(UserAccount user) {
        resetTokens.expireOutstanding(user, Instant.now());
        String raw = TokenHash.randomToken();
        resetTokens.save(new PasswordResetToken(
                user,
                TokenHash.sha256(raw),
                Instant.now().plus(properties.security().passwordResetTtl())
        ));
        mailSender.send(
                user.getEmail(),
                templates.passwordResetSubject(),
                templates.passwordResetBody(user.getFirstName(), raw)
        );
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().isBlank()) {
            smsSender.send(user.getPhoneNumber(), templates.passwordResetSms());
        }
    }

    private void requireRateLimit(String key) {
        if (!rateLimitService.allow(key)) {
            throw ApiException.rateLimited("Too many requests. Please try again later.");
        }
    }

    private static String blankToNull(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }
}
