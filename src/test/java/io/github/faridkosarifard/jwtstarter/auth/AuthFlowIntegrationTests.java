package io.github.faridkosarifard.jwtstarter.auth;

import tools.jackson.databind.JsonNode;
import io.github.faridkosarifard.jwtstarter.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthFlowIntegrationTests extends AbstractIntegrationTest {

    @Test
    void registerVerifyLoginAndReadProfile() throws Exception {
        RegisteredUser user = registerAndVerify("flow");
        JsonNode tokens = login(user.username(), user.password());
        assertThat(tokens.get("tokenType").asText()).isEqualTo("Bearer");
        assertThat(tokens.get("expiresIn").asLong()).isEqualTo(900);
        mockMvc.perform(get("/api/v1/users/me").header(HttpHeaders.AUTHORIZATION, bearer(tokens)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.email()))
                .andExpect(jsonPath("$.username").value(user.username()))
                .andExpect(jsonPath("$.enabled").value(true))
                .andExpect(jsonPath("$.roles[0]").value("ROLE_USER"));
    }

    @Test
    void loginIsAlsoPossibleWithEmail() throws Exception {
        RegisteredUser user = registerAndVerify("emailogin");
        login(user.email(), user.password());
    }

    @Test
    void cannotLoginBeforeEmailVerification() throws Exception {
        String username = uniqueName("pending");
        String email = username + "@example.com";
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ann","lastName":"Lee","username":"%s","email":"%s","password":"ValidPass1"}
                                """.formatted(username, email)))
                .andExpect(status().isCreated());
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"ValidPass1"}
                                """.formatted(username)))
                .andExpect(status().isForbidden())
                .andExpect(jsonPath("$.title").value("Forbidden"));
    }

    @Test
    void duplicateEmailAndUsernameAreRejected() throws Exception {
        RegisteredUser user = registerAndVerify("dup");
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ann","lastName":"Lee","username":"%s","email":"other-%s","password":"ValidPass1"}
                                """.formatted(user.username(), user.email())))
                .andExpect(status().isConflict());
        String otherUser = uniqueName("dup2");
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"firstName":"Ann","lastName":"Lee","username":"%s","email":"%s","password":"ValidPass1"}
                                """.formatted(otherUser, user.email())))
                .andExpect(status().isConflict());
    }

    @Test
    void refreshRotatesTokensAndLogoutRevokesThem() throws Exception {
        RegisteredUser user = registerAndVerify("refresh");
        JsonNode first = login(user.username(), user.password());
        String refresh = first.get("refreshToken").asText();
        var rotated = mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken":"%s"}
                                """.formatted(refresh)))
                .andExpect(status().isOk())
                .andReturn();
        JsonNode second = objectMapper.readTree(rotated.getResponse().getContentAsString());
        assertThat(second.get("refreshToken").asText()).isNotEqualTo(refresh);
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken":"%s"}
                                """.formatted(refresh)))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.detail").value(org.hamcrest.Matchers.containsString("reuse")));
        mockMvc.perform(post("/api/v1/auth/logout")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken":"%s"}
                                """.formatted(second.get("refreshToken").asText())))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken":"%s"}
                                """.formatted(second.get("refreshToken").asText())))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void logoutAllRevokesCurrentRefreshToken() throws Exception {
        RegisteredUser user = registerAndVerify("logoutall");
        JsonNode tokens = login(user.username(), user.password());
        mockMvc.perform(post("/api/v1/auth/logout-all")
                        .header(HttpHeaders.AUTHORIZATION, bearer(tokens)))
                .andExpect(status().isOk());
        mockMvc.perform(post("/api/v1/auth/refresh")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"refreshToken":"%s"}
                                """.formatted(tokens.get("refreshToken").asText())))
                .andExpect(status().isUnauthorized());
    }
}
