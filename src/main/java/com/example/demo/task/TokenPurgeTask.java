package com.example.demo.task;

import com.example.demo.persistence.dao.PasswordResetTokenRepository;
import com.example.demo.persistence.dao.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Date;

/**
 * demo
 * Created by Farid Kosarifard
 * Date: 10/9/2023
 * Time: 5:14 PM
 */
public class TokenPurgeTask {
    @Service
    @Transactional
    public class TokensPurgeTask {

        @Autowired
        VerificationTokenRepository tokenRepository;

        @Autowired
        PasswordResetTokenRepository passwordTokenRepository;

        @Scheduled(cron = "0 0 5 * * ?")
        public void purgeExpired() {
            Date now = Date.from(Instant.now());
            passwordTokenRepository.deleteAllExpiredSince(now);
            tokenRepository.deleteAllExpiredSince(now);
        }
    }
}
