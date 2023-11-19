package com.example.demo.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.example.demo.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 5:19 PM
 */

@Component
@Slf4j
public class TokenProvider {

    @Autowired
    private UserRepository userRepository;
    //TODO CHANGE THE ISSUER AT THE END
    @Value("${jwt.issuer}")
    private String issuer;

    public String getToken(String username) throws IllegalArgumentException, JWTCreationException {
        log.info("getToken");
        String secretKey = userRepository.findByUsername(username).getSecret();
        System.out.println(secretKey);
        return JWT.create()
                .withClaim("username", username)
                .withIssuedAt(new Date())
                .withIssuer(issuer)
                .sign(Algorithm.HMAC256(secretKey));
    }

    public String verifyToken(String token) throws JWTVerificationException {
        log.info("verifyToken");
        String userName= JWT.decode(token).getClaim("username").asString();
        String secretKey = userRepository.findByUsername(userName).getSecret();
        return JWT.require(Algorithm.HMAC256(secretKey))
                .withIssuer(issuer)
                .build().verify(token).getClaim("username").asString();
    }
}
