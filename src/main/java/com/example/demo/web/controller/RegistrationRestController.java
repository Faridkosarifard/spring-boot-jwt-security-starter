package com.example.demo.web.controller;

import com.example.demo.common.MailHtmlContent;
import com.example.demo.common.TokenProvider;
import com.example.demo.persistence.model.Privilege;
import com.example.demo.persistence.model.Role;
import com.example.demo.persistence.model.User;
import com.example.demo.registration.OnRegistrationCompleteEvent;

import com.example.demo.service.CustomAuthenticationService;
import com.example.demo.service.IUserService;
import com.example.demo.sping.ISecurityUserService;
import com.example.demo.web.dto.PasswordDto;
import com.example.demo.web.dto.UserDto;
import com.example.demo.web.util.GenericResponse;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.stream.Collectors;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 5:17 PM
 */
@RestController
public class RegistrationRestController {

    private IUserService userService;

    @Autowired
    private ISecurityUserService securityUserService;
    private final TokenProvider tokenProvider;

    private final AuthenticationManager authenticationManager;

    private JavaMailSender mailSender;

    private ApplicationEventPublisher eventPublisher;

    private Environment env;

    public RegistrationRestController(IUserService userService, TokenProvider tokenProvider,  AuthenticationManager authenticationManager, JavaMailSender mailSender, ApplicationEventPublisher eventPublisher, Environment env) {
        this.userService = userService;
        this.tokenProvider=tokenProvider;
        this.authenticationManager = authenticationManager;
        this.mailSender = mailSender;
        this.eventPublisher = eventPublisher;
        this.env = env;
    }

    @PostMapping("/user/registration")
    public Object registerUserAccount(@RequestBody final UserDto accountDto, final HttpServletRequest request) {
        //LOGGER.debug("Registering user account with information: {}", accountDto);
        User registered = userService.registerNewUserAccount(accountDto);
        eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
        return new GenericResponse("success");
    }

    @GetMapping("/registrationConfirm")
    public Object confirmRegistration(@RequestParam("token") final String token) throws UnsupportedEncodingException {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
            authWithoutPassword(user);
            System.out.println("OK");
            //TODO REDIRECT TO HOME PAGE
            return HttpStatus.OK;
        }
        System.out.println("Not ok ");
        //TODO REDIRECT TO LOGIN PAGE
        return HttpStatus.NOT_ACCEPTABLE;
    }

    @PostMapping("/user/resetPassword")
    public GenericResponse resetPassword(final HttpServletRequest request, @RequestBody final String userEmail) {
        final User user = userService.findUserByEmail(userEmail);
        if (user != null) {
            final String token = UUID.randomUUID().toString();
            userService.createPasswordResetTokenForUser(user, token);
            mailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));
        }
        return new GenericResponse("ٍEmail Sent");
    }

    @GetMapping("/user/forgetPassword")
    public Object showChangePasswordPage(final ModelMap model, @RequestBody final String token) {
        final String result = securityUserService.validatePasswordResetToken(token);
        if(result != null) {
            String messageKey = "auth.message." + result;
            model.addAttribute("messageKey", messageKey);
            //TODO REDIRECT TO ERROR PAGE AND SEND THE MSG TO UI
            return new ModelAndView("redirect:/login", model);
        } else {
            model.addAttribute("token", token);
            //TODO REDIRECT TO CHANGE PASSWORD PAGE WITH TOKEN
            return new ModelAndView("redirect:/updatePassword");
        }
    }


    @PostMapping("/user/savePassword")
    public GenericResponse savePassword(final Locale locale, @RequestBody PasswordDto passwordDto) {
        final String result = securityUserService.validatePasswordResetToken(passwordDto.getToken());
        if(result != null) {
            return new GenericResponse("NULL/expire");
        }
        Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if(user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            return new GenericResponse("Password Successfully change");
        } else {
            return new GenericResponse("token invalid");
        }
    }

    @GetMapping("/user/changePassword")
    public Object changePassword( @RequestBody PasswordDto passwordDto){
        User user =  userService.findUserByEmail(passwordDto.getEmail());
        if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
            System.out.println("not match");
        }
        userService.changeUserPassword(user, passwordDto.getNewPassword());
        return "succ";
    }

    @GetMapping("/login")
    public String login(@RequestBody UserDto userEntity) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUserName(), userEntity.getPassword()));
        return tokenProvider.getToken(userEntity.getUserName());
    }

    private MimeMessage constructResetTokenEmail(final String contextPath, final String token, final User user) {
        final String url = contextPath + "/user/changePassword?token=" + token;
        final String message = MailHtmlContent.getInstance().forgetPasswordHtmlContent(user.getFirstName(),user.getLastName(),url);
        return constructEmail("فراموشی رمز عبور", message , user);
    }
    @SneakyThrows
    private MimeMessage constructEmail(String subject, String body, User user) {
        MimeMessage email = mailSender.createMimeMessage();
        email.setRecipients(MimeMessage.RecipientType.TO, user.getEmail());
        email.setSubject(subject);
        email.setContent(body, "text/html; charset=utf-8");
        email.setFrom(env.getProperty("support.email"));
        return email;
    }
    private String getAppUrl(HttpServletRequest request) {
        return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }


    public void authWithoutPassword(User user) {

        List<Privilege> privileges = user.getRoles()
                .stream()
                .map(Role::getPrivileges)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toList());

        List<GrantedAuthority> authorities = privileges.stream()
                .map(p -> new SimpleGrantedAuthority(p.getName()))
                .collect(Collectors.toList());

        Authentication authentication = new UsernamePasswordAuthenticationToken(user, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
}
