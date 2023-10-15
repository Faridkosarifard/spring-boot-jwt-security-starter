package com.example.demo.registration.listiener;

import com.example.demo.common.MailHtmlContent;
import com.example.demo.persistence.model.User;
import com.example.demo.registration.OnRegistrationCompleteEvent;
import com.example.demo.service.IUserService;
import jakarta.mail.internet.MimeMessage;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 4:38 PM
 */
@Component
public class RegistrationListener implements ApplicationListener<OnRegistrationCompleteEvent> {
    @Autowired
    private IUserService service;

    @Autowired
    private MessageSource messages;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private Environment env;

    // API

    @Override
    public void onApplicationEvent(final OnRegistrationCompleteEvent event) {
        this.confirmRegistration(event);
    }

    private void confirmRegistration(final OnRegistrationCompleteEvent event) {
        final User user = event.getUser();
        final String token = UUID.randomUUID().toString();
        service.createVerificationTokenForUser(user, token);

        final MimeMessage email = constructEmailMessage(event, user, token);
        mailSender.send(email);
    }

    //
    @SneakyThrows
    private MimeMessage constructEmailMessage(final OnRegistrationCompleteEvent event, final User user, final String token) {
        final String recipientAddress = user.getEmail();
        final String subject = "تایید عضویت";
        final String confirmationUrl = event.getAppUrl() + "/registrationConfirm?token=" + token;
        //final String message = messages.getMessage("message.regSuccLink", null, "ثبت نام شا با موفقیت انجام شده است. برای فعال سازی و استفاده از امکانات سایت لطفا بر روی لینک زیر کلیک کنید.", event.getLocale());
        MimeMessage email = mailSender.createMimeMessage();
        email.setRecipients(MimeMessage.RecipientType.TO, recipientAddress);
        email.setSubject(subject);
        email.setContent(MailHtmlContent.getInstance().verifyMailHtmlContent(user.getFirstName(), user.getLastName(), confirmationUrl), "text/html; charset=utf-8");
        email.setFrom(env.getProperty("support.email"));
        return email;
    }

}
