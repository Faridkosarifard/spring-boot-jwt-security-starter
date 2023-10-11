package com.example.demo.registration;

import com.example.demo.persistence.model.User;
import org.springframework.context.ApplicationEvent;


import java.util.Locale;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 4:36 PM
 */
public class OnRegistrationCompleteEvent extends ApplicationEvent {

    private final String appUrl;
    private final Locale locale;
    private final User user;

    public OnRegistrationCompleteEvent(final User user, final Locale locale, final String appUrl) {
        super(user);
        this.user = user;
        this.locale = locale;
        this.appUrl = appUrl;
    }
    public String getAppUrl() {
        return appUrl;
    }

    public Locale getLocale() {
        return locale;
    }

    public User getUser() {
        return user;
    }
}
