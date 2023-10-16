package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/16/2023
 * Time: 6:37 PM
 */
@Service
public class CustomAuthenticationService {
    private final AuthenticationManager authenticationManager;

    @Autowired
    public CustomAuthenticationService(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public void authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}

