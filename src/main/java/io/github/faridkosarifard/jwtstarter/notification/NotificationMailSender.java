package io.github.faridkosarifard.jwtstarter.notification;

public interface NotificationMailSender {

    void send(String to, String subject, String body);
}
