package com.example.demo.api;

import com.example.demo.repository.UserRepository;
import com.example.demo.domain.model.User;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminAPI {
    private final UserRepository userRepository;

    public AdminAPI(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @GetMapping("/")
    public User get() {
        return userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
    }
}
