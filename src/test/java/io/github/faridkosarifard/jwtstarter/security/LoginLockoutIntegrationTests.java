package io.github.faridkosarifard.jwtstarter.security;

import io.github.faridkosarifard.jwtstarter.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LoginLockoutIntegrationTests extends AbstractIntegrationTest {

    @Test
    void locksAccountAfterConfiguredFailedAttempts() throws Exception {
        RegisteredUser user = registerAndVerify("lock");
        for (int i = 0; i < 4; i++) {
            mockMvc.perform(post("/api/v1/auth/login")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content("""
                                    {"username":"%s","password":"WrongPass1"}
                                    """.formatted(user.username())))
                    .andExpect(status().isUnauthorized());
        }
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"WrongPass1"}
                                """.formatted(user.username())))
                .andExpect(status().isLocked())
                .andExpect(jsonPath("$.title").value("Account Locked"));
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"%s"}
                                """.formatted(user.username(), user.password())))
                .andExpect(status().isLocked());
    }

    @Test
    void unknownUserDoesNotRevealAccountExistence() throws Exception {
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"missing-user","password":"ValidPass1"}
                                """))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value("Invalid username or password"));
    }
}
