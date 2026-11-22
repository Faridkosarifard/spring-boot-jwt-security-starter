package io.github.faridkosarifard.jwtstarter.support;

import tools.jackson.databind.JsonNode;
import tools.jackson.databind.json.JsonMapper;
import io.github.faridkosarifard.jwtstarter.notification.NotificationInbox;
import io.github.faridkosarifard.jwtstarter.security.RateLimitService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@ContextConfiguration(initializers = DatabaseTestInitializer.class)
public abstract class AbstractIntegrationTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected JsonMapper objectMapper;

    @Autowired
    protected NotificationInbox inbox;

    @Autowired
    protected RateLimitService rateLimitService;

    @BeforeEach
    void resetSharedState() {
        inbox.clear();
        rateLimitService.clear();
    }

    protected String uniqueName(String prefix) {
        return prefix + UUID.randomUUID().toString().replace("-", "").substring(0, 12);
    }

    protected RegisteredUser registerAndVerify(String prefix) throws Exception {
        String username = uniqueName(prefix);
        String email = username + "@example.com";
        String password = "ValidPass1";
        String body = """
                {
                  "firstName": "Ada",
                  "lastName": "Lovelace",
                  "username": "%s",
                  "email": "%s",
                  "password": "%s",
                  "phoneNumber": "+15551234567"
                }
                """.formatted(username, email, password);
        mockMvc.perform(post("/api/v1/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated());
        String token = inbox.latestTokenFor(email, "Verify").orElseThrow();
        mockMvc.perform(get("/api/v1/auth/verify-email").param("token", token))
                .andExpect(status().isOk());
        return new RegisteredUser(username, email, password);
    }

    protected JsonNode login(String username, String password) throws Exception {
        MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"username":"%s","password":"%s"}
                                """.formatted(username, password)))
                .andExpect(status().isOk())
                .andReturn();
        return objectMapper.readTree(result.getResponse().getContentAsString());
    }

    protected String bearer(JsonNode tokens) {
        return "Bearer " + tokens.get("accessToken").asText();
    }

    public record RegisteredUser(String username, String email, String password) {
    }
}
