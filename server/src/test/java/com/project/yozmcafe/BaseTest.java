package com.project.yozmcafe;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;

/**
 * Application Context들이 필요할 때 상속하면 됩니다.
 * Repository, Service 레이어 통합 테스트용으로 쓰면 됩니다.
 */
@SpringBootTest
@ActiveProfiles("test")
public abstract class BaseTest {

    @Container
    protected static MySQLContainer container;

    static {
        container = (MySQLContainer) new MySQLContainer("mysql:8.0")
                .withDatabaseName("yozm-cafe")
                .withEnv("MYSQL_ROOT_PASSWORD", "test")
                .withReuse(true);

        container.start();
    }

    @DynamicPropertySource
    static void configureProperties(final DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", container::getJdbcUrl);
        registry.add("spring.datasource.username", () -> "root");
        registry.add("spring.datasource.password", () -> "test");
    }

    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    void delete() {
        dataInitializer.deleteAll();
    }
}
