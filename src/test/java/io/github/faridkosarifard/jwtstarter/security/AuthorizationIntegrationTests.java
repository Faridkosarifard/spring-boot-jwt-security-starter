package io.github.faridkosarifard.jwtstarter.security;

import tools.jackson.databind.JsonNode;
import io.github.faridkosarifard.jwtstarter.support.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AuthorizationIntegrationTests extends AbstractIntegrationTest {

    @Test
    void protectedEndpointsRequireAuthentication() throws Exception {
        mockMvc.perform(get("/api/v1/users/me"))
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.type").value(org.hamcrest.Matchers.containsString("unauthorized")));
        mockMvc.perform(get("/api/v1/admin/users"))
                .andExpect(status().isUnauthorized());
        mockMvc.perform(get("/api/v1/demo/authenticated"))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void userCanAccessUserEndpointsButNotAdminOnes() throws Exception {
        RegisteredUser user = registerAndVerify("authz");
        JsonNode tokens = login(user.username(), user.password());
        String header = bearer(tokens);
        mockMvc.perform(get("/api/v1/demo/authenticated").header(HttpHeaders.AUTHORIZATION, header))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/demo/user").header(HttpHeaders.AUTHORIZATION, header))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/demo/moderator").header(HttpHeaders.AUTHORIZATION, header))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/demo/admin").header(HttpHeaders.AUTHORIZATION, header))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/demo/admin-write").header(HttpHeaders.AUTHORIZATION, header))
                .andExpect(status().isForbidden());
        mockMvc.perform(get("/api/v1/admin/users").header(HttpHeaders.AUTHORIZATION, header))
                .andExpect(status().isForbidden());
    }

    @Test
    void adminCanManageUsersAndReachAdminDemos() throws Exception {
        JsonNode adminTokens = login("admin", "AdminPassw0rd!");
        String adminHeader = bearer(adminTokens);
        mockMvc.perform(get("/api/v1/demo/admin").header(HttpHeaders.AUTHORIZATION, adminHeader))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/demo/admin-write").header(HttpHeaders.AUTHORIZATION, adminHeader))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/demo/moderator").header(HttpHeaders.AUTHORIZATION, adminHeader))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/v1/admin/users").header(HttpHeaders.AUTHORIZATION, adminHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray());

        RegisteredUser user = registerAndVerify("managed");
        JsonNode listed = objectMapper.readTree(mockMvc.perform(get("/api/v1/admin/users")
                        .header(HttpHeaders.AUTHORIZATION, adminHeader)
                        .param("size", "100"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString());
        long userId = -1;
        for (JsonNode node : listed.get("content")) {
            if (user.email().equals(node.get("email").asText())) {
                userId = node.get("id").asLong();
            }
        }
        mockMvc.perform(get("/api/v1/admin/users/{id}", userId).header(HttpHeaders.AUTHORIZATION, adminHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(user.email()));
        mockMvc.perform(patch("/api/v1/admin/users/{id}", userId)
                        .header(HttpHeaders.AUTHORIZATION, adminHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"enabled":false,"roles":["USER"]}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabled").value(false));
        mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"%s"}
                                """.formatted(user.username(), user.password())))
                .andExpect(status().isForbidden());
        mockMvc.perform(patch("/api/v1/admin/users/{id}", userId)
                        .header(HttpHeaders.AUTHORIZATION, adminHeader)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"enabled":true,"locked":true}
                                """))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locked").value(true));
        mockMvc.perform(post("/api/v1/admin/users/{id}/unlock", userId)
                        .header(HttpHeaders.AUTHORIZATION, adminHeader))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.locked").value(false));
        mockMvc.perform(get("/api/v1/admin/users/{id}", 999999L).header(HttpHeaders.AUTHORIZATION, adminHeader))
                .andExpect(status().isNotFound());
    }
}
