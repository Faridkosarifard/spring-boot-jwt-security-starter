package io.github.faridkosarifard.jwtstarter.notification;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@ConditionalOnProperty(name = "app.mail.mode", havingValue = "logging", matchIfMissing = true)
public class LoggingMailSender implements NotificationMailSender {

    private static final Logger log = LoggerFactory.getLogger(LoggingMailSender.class);

    private final NotificationInbox inbox;

    public LoggingMailSender(NotificationInbox inbox) {
        this.inbox = inbox;
    }

    @Override
    public void send(String to, String subject, String body) {
        inbox.record(new MailMessage(to, subject, body, Instant.now()));
        log.info("Mail to {} | {} | {}", to, subject, body.replaceAll("\\s+", " "));
    }
}
