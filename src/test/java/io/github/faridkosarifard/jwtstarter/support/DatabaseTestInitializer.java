package io.github.faridkosarifard.jwtstarter.support;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.LinkedHashMap;
import java.util.Map;

public class DatabaseTestInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    private static final Logger log = LoggerFactory.getLogger(DatabaseTestInitializer.class);
    private static final PostgreSQLContainer<?> POSTGRES;
    private static final boolean DOCKER_AVAILABLE;

    static {
        PostgreSQLContainer<?> container = null;
        boolean available = false;
        try {
            container = new PostgreSQLContainer<>("postgres:16-alpine");
            container.start();
            available = true;
            log.info("Started PostgreSQL Testcontainer at {}", container.getJdbcUrl());
        } catch (Throwable ex) {
            log.warn("Docker is not available; integration tests will use in-memory H2 ({})", ex.toString());
        }
        POSTGRES = container;
        DOCKER_AVAILABLE = available;
    }

    @Override
    public void initialize(ConfigurableApplicationContext context) {
        if (!DOCKER_AVAILABLE || POSTGRES == null) {
            return;
        }
        Map<String, Object> properties = new LinkedHashMap<>();
        properties.put("spring.datasource.url", POSTGRES.getJdbcUrl());
        properties.put("spring.datasource.username", POSTGRES.getUsername());
        properties.put("spring.datasource.password", POSTGRES.getPassword());
        properties.put("spring.datasource.driver-class-name", "org.postgresql.Driver");
        properties.put("spring.jpa.properties.hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect");
        context.getEnvironment().getPropertySources()
                .addFirst(new MapPropertySource("testcontainers", properties));
    }
}
