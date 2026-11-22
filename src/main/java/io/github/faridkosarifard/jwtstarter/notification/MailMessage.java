package io.github.faridkosarifard.jwtstarter.notification;

import java.time.Instant;

public record MailMessage(String to, String subject, String body, Instant sentAt) {
}
