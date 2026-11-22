package io.github.faridkosarifard.jwtstarter.notification;

public interface SmsSender {

    void send(String phoneNumber, String body);
}
