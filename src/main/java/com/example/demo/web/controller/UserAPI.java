package com.example.demo.web.controller;

import com.example.demo.persistence.dao.UserRepository;
import com.example.demo.persistence.model.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/*
    Bahador, Amirsam
 */
@RestController
@RequestMapping("/users")
public class UserAPI {
    private final UserRepository userRepository;

    public UserAPI(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    @GetMapping("/")
    public User get() {
        return userRepository.findByUserName(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
