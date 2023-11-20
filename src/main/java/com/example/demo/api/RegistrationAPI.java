package com.example.demo.api;

import com.example.demo.common.MailHtmlContent;
import com.example.demo.common.TokenProvider;
import com.example.demo.domain.model.Privilege;
import com.example.demo.domain.model.Role;
import com.example.demo.domain.model.User;
import com.example.demo.domain.view.Views;
import com.example.demo.registration.OnRegistrationCompleteEvent;
import com.example.demo.service.IUserService;
import com.example.demo.spring.ISecurityUserService;
import com.example.demo.domain.dto.PasswordDto;
import com.example.demo.domain.dto.UserDto;
import com.example.demo.exception.UserAlreadyExistException;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
@Slf4j
@RequestMapping("/user")
@Tag(name = "User Registration", description = "the registration API for users")
public class RegistrationAPI {

    private final IUserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;
    private final JavaMailSender mailSender;
    private final ApplicationEventPublisher eventPublisher;
    private final Environment env;

    @Autowired
    private ISecurityUserService securityUserService;

    public RegistrationAPI(IUserService userService, TokenProvider tokenProvider, AuthenticationManager authenticationManager, JavaMailSender mailSender, ApplicationEventPublisher eventPublisher, Environment env, ISecurityUserService securityUserService) {
        this.userService = userService;
        this.tokenProvider = tokenProvider;
        this.authenticationManager = authenticationManager;
        this.mailSender = mailSender;
        this.eventPublisher = eventPublisher;
        this.env = env;
    }

    @Operation(description = "Registration")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "406", description = "Not Acceptable"),
            @ApiResponse(responseCode = "409", description = "Conflict")
    })
    @PostMapping("/registration")
    @JsonView(Views.Registration.class)
    public ResponseEntity<HttpStatus> registerUserAccount(@RequestBody final UserDto accountDto, final HttpServletRequest request) {
        try {
            if (accountDto.getUsername() != null && accountDto.getPassword() != null) {
                log.info("Registering user account with information: {}", accountDto);
                User registered = userService.registerNewUserAccount(accountDto);
                eventPublisher.publishEvent(new OnRegistrationCompleteEvent(registered, request.getLocale(), getAppUrl(request)));
                log.info("Registration completed");
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                log.error("Registration user failed: username or password empty {}", accountDto);
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            }
        } catch (Exception e) {
            log.error(new UserAlreadyExistException(e.getMessage()).getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping("/registration-confirm")
    public Object confirmRegistration(@RequestParam("token") final String token) throws UnsupportedEncodingException {
        final String result = userService.validateVerificationToken(token);
        if (result.equals("valid")) {
            final User user = userService.getUser(token);
            authWithoutPassword(user);
            log.info("Validation for user {} with email {} ist completed.", user.getUsername(), user.getEmail());
            //TODO REDIRECT TO HOME PAGE
            return HttpStatus.OK;
        }
        log.error("Validation failed, Token {} not valid.", token);
        //TODO REDIRECT TO LOGIN PAGE
        return HttpStatus.NOT_ACCEPTABLE;
    }

    @GetMapping("/validation")
    @Hidden
    public Object showChangePasswordPage(@RequestParam("token") final String token) {
        try {
            final String result = securityUserService.validatePasswordResetToken(token);
            if (result != null) {
                log.error("Token {} not valid", token);
                //TODO REDIRECT TO ERROR PAGE AND SEND THE MSG TO UI
                return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
            } else {
                log.info("Password Token verified and Redirect to Reset Password Page");
                //TODO REDIRECT TO CHANGE PASSWORD PAGE WITH TOKEN
                return ResponseEntity.status(HttpStatus.ACCEPTED).build();
            }
        } catch (Exception e) {
            log.error("An error occurred." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/forget-password")
    @JsonView(Views.ResetPassword.class)
    public ResponseEntity<HttpStatus> resetPassword(final HttpServletRequest request, @RequestBody final UserDto userDto) {
        final User user = userService.findUserByEmail(userDto.getEmail());
        try {
            if (user != null) {
                final String token = UUID.randomUUID().toString();
                userService.createPasswordResetTokenForUser(user, token);
                mailSender.send(constructResetTokenEmail(getAppUrl(request), token, user));
                return ResponseEntity.status(HttpStatus.OK).build();
            } else {
                log.error("Not Found user account with email {}:", userDto.getEmail());
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
        } catch (DataIntegrityViolationException ex) {
            log.error("Token already exists for this user.{}", userDto.getEmail() + "Error msg: " + ex.getMessage());
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        } catch (Exception e) {
            log.error("An error occurred." + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/reset-password")
    @JsonView(Views.SavePassword.class)
    public ResponseEntity<HttpStatus> savePassword(final Locale locale, @RequestBody PasswordDto passwordDto) {
        final String result = securityUserService.validatePasswordResetToken(passwordDto.getToken());
        if (result != null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Optional<User> user = userService.getUserByPasswordResetToken(passwordDto.getToken());
        if (user.isPresent()) {
            userService.changeUserPassword(user.get(), passwordDto.getNewPassword());
            userService.deletePasswordResetToken(user.get());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).build();
        }
    }


    @PostMapping("/change-password")
    @JsonView(Views.ChangePassword.class)
    public ResponseEntity<HttpStatus> changePassword(@RequestBody PasswordDto passwordDto) {
        if (passwordDto.getNewPassword() != null && passwordDto.getOldPassword() != null && passwordDto.getEmail() != null) {
            User user = userService.findUserByEmail(passwordDto.getEmail());
            if (!userService.checkIfValidOldPassword(user, passwordDto.getOldPassword())) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
            userService.changeUserPassword(user, passwordDto.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).build();
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    @PostMapping("/login")
    @JsonView(Views.Login.class)
    public ResponseEntity<User> login(@RequestBody UserDto userEntity) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword()));
        String token = tokenProvider.getToken(userEntity.getUsername());
        User user = userService.findOne(userEntity);
        user.setToken(token);
        return ResponseEntity.ok(user);
    }

    private MimeMessage constructResetTokenEmail(final String contextPath, final String token, final User user) {
        final String url = contextPath + "/user/validation?token=" + token;
        final String message = MailHtmlContent.getInstance().forgetPasswordHtmlContent(user.getFirstName(), user.getLastName(), url);
        return constructEmail("فراموشی رمز عبور", message, user);
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
