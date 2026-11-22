package io.github.faridkosarifard.jwtstarter.notification;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class NotificationInbox {

    private static final Pattern TOKEN = Pattern.compile("[?&]token=([A-Za-z0-9\\-_]+)");

    private final List<MailMessage> mail = new CopyOnWriteArrayList<>();
    private final List<SmsMessage> sms = new CopyOnWriteArrayList<>();

    public void record(MailMessage message) {
        mail.add(message);
    }

    public void record(SmsMessage message) {
        sms.add(message);
    }

    public List<MailMessage> mailTo(String email) {
        return mail.stream().filter(message -> message.to().equalsIgnoreCase(email)).toList();
    }

    public List<SmsMessage> smsTo(String phone) {
        return sms.stream().filter(message -> message.to().equals(phone)).toList();
    }

    public Optional<String> latestTokenFor(String email, String subjectContains) {
        List<MailMessage> matches = new ArrayList<>(mailTo(email));
        for (int i = matches.size() - 1; i >= 0; i--) {
            MailMessage message = matches.get(i);
            if (message.subject().toLowerCase().contains(subjectContains.toLowerCase())) {
                Matcher matcher = TOKEN.matcher(message.body());
                if (matcher.find()) {
                    return Optional.of(matcher.group(1));
                }
            }
        }
        return Optional.empty();
    }

    public void clear() {
        mail.clear();
        sms.clear();
    }
}
