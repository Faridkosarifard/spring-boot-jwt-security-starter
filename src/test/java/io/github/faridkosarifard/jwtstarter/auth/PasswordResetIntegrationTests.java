package io.github.faridkosarifard.jwtstarter.auth;

import tools.jackson.databind.JsonNode;
import io.github.faridkosarifard.jwtstarter.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PasswordResetIntegrationTests extends AbstractIntegrationTest {

    @Test
    void forgotPasswordSendsEmailAndSmsThenResetWorksOnce() throws Exception {
        RegisteredUser user = registerAndVerify("reset");
        mockMvc.perform(post("/api/v1/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"%s"}
                                """.formatted(user.email())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("If an account exists")));
        assertThat(inbox.smsTo("+15551234567")).isNotEmpty();
        String token = inbox.latestTokenFor(user.email(), "Reset").orElseThrow();
        mockMvc.perform(post("/api/v1/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"token":"%s","newPassword":"NewValid2"}
                                """.formatted(token)))
                .andExpect(status().isOk());
        login(user.username(), "NewValid2");
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"ValidPass1"}
                                """.formatted(user.username())))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(post("/api/v1/auth/reset-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"token":"%s","newPassword":"Another3x"}
                                """.formatted(token)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void forgotPasswordDoesNotRevealUnknownEmails() throws Exception {
        mockMvc.perform(post("/api/v1/auth/forgot-password")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"email":"missing@example.com"}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value(org.hamcrest.Matchers.containsString("If an account exists")));
        assertThat(inbox.mailTo("missing@example.com")).isEmpty();
    }

    @Test
    void changePasswordRevokesExistingSessions() throws Exception {
        RegisteredUser user = registerAndVerify("changepw");
        JsonNode tokens = login(user.username(), user.password());
        mockMvc.perform(post("/api/v1/users/me/password")
                        .header(HttpHeaders.AUTHORIZATION, bearer(tokens))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"currentPassword":"ValidPass1","newPassword":"Changed9x"}
                                """))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken":"%s"}
                                """.formatted(tokens.get("refreshToken").asText())))
                .andExpect(status().isUnauthorized());
        login(user.username(), "Changed9x");
    }
}
