package io.github.faridkosarifard.jwtstarter.notification;

import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import org.springframework.stereotype.Component;

@Component
public class EmailTemplates {

    private final AppProperties properties;

    public EmailTemplates(AppProperties properties) {
        this.properties = properties;
    }

    public String verificationSubject() {
        return "Verify your email address";
    }

    public String verificationBody(String firstName, String token) {
        String link = properties.mail().baseUrl() + "/api/v1/auth/verify-email?token=" + token;
        return """
                Hello %s,

                Thanks for registering. Please confirm your email address by opening the link below:

                %s

                This link expires after a limited time and can be used only once.
                If you did not create an account, you can ignore this message.
                """.formatted(firstName, link);
    }

    public String passwordResetSubject() {
        return "Reset your password";
    }

    public String passwordResetBody(String firstName, String token) {
        String link = properties.mail().baseUrl() + "/api/v1/auth/reset-password?token=" + token;
        return """
                Hello %s,

                We received a request to reset your password. Use the following one-time token with
                POST /api/v1/auth/reset-password, or open the link below:

                %s

                If you did not request a password reset, you can ignore this message.
                """.formatted(firstName, link);
    }

    public String passwordResetSms() {
        return "A password reset was requested for your account. If this was you, check your email for the reset link. If not, you can ignore this message.";
    }
}
