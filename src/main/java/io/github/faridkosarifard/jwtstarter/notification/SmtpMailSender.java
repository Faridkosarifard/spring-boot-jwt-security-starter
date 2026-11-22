package io.github.faridkosarifard.jwtstarter.notification;

import io.github.faridkosarifard.jwtstarter.config.AppProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@ConditionalOnProperty(name = "app.mail.mode", havingValue = "smtp")
public class SmtpMailSender implements NotificationMailSender {

    private static final Logger log = LoggerFactory.getLogger(SmtpMailSender.class);

    private final JavaMailSender mailSender;
    private final AppProperties properties;
    private final NotificationInbox inbox;

    public SmtpMailSender(JavaMailSender mailSender, AppProperties properties, NotificationInbox inbox) {
        this.mailSender = mailSender;
        this.properties = properties;
        this.inbox = inbox;
    }

    @Override
    public void send(String to, String subject, String body) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(properties.mail().from());
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        inbox.record(new MailMessage(to, subject, body, Instant.now()));
        log.info("SMTP mail sent to {}", to);
    }
}
