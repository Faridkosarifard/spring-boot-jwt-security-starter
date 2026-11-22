package io.github.faridkosarifard.jwtstarter.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
public class LoggingSmsSender implements SmsSender {

    private static final Logger log = LoggerFactory.getLogger(LoggingSmsSender.class);

    private final NotificationInbox inbox;

    public LoggingSmsSender(NotificationInbox inbox) {
        this.inbox = inbox;
    }

    @Override
    public void send(String phoneNumber, String body) {
        inbox.record(new SmsMessage(phoneNumber, body, Instant.now()));
        log.info("SMS to {} | {}", phoneNumber, body);
    }
}
