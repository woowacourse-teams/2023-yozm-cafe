package com.project.yozmcafe;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@Testcontainers
public abstract class BaseTest {

    @Container
    protected static final GenericContainer container = new MySQLContainer("mysql");

    @Autowired
    private DataInitializer dataInitializer;

    @BeforeEach
    void delete() {
        dataInitializer.deleteAll();
    }
}
