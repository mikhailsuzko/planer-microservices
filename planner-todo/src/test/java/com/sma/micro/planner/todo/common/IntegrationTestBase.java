package com.sma.micro.planner.todo.common;

import com.sma.micro.planner.todo.annotation.IT;
import org.junit.jupiter.api.BeforeAll;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@IT
public abstract class IntegrationTestBase {
    private static final PostgreSQLContainer<?> container =
            new PostgreSQLContainer<>(DockerImageName.parse("postgres:15.2-bullseye"));
    @MockBean
    private JwtDecoder jwtDecoder;

    @BeforeAll
    static void beforeAll() {
        container.start();
    }

    @DynamicPropertySource
    static void postgresProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
    }
}
