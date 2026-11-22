package io.github.faridkosarifard.jwtstarter.notification;

import java.time.Instant;

public record SmsMessage(String to, String body, Instant sentAt) {
}
