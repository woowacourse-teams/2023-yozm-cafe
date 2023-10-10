package com.project.yozmcafe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Component
@Profile("test")
public class DataInitializer {

    private static final int OFF = 0;
    private static final int ON = 1;
    private static final String FLYWAY = "flyway";
    private static final String TRUNCATE = "TRUNCATE ";

    @PersistenceContext
    private EntityManager em;

    private final List<String> truncationDMLs = new ArrayList<>();

    @BeforeEach
    @Transactional
    public void deleteAll() {
        if (truncationDMLs.isEmpty()) {
            init();
        }

        setForeignKeyEnabled(OFF);
        truncateAllTables();
        setForeignKeyEnabled(ON);
    }

    private void setForeignKeyEnabled(final int enabled) {
        em.createNativeQuery("SET foreign_key_checks = " + enabled).executeUpdate();
    }

    private void truncateAllTables() {
        truncationDMLs.stream()
                .map(em::createNativeQuery)
                .forEach(Query::executeUpdate);
    }

    private void init() {
        final List<String> tableNames = em.createNativeQuery("SHOW TABLES ").getResultList();

        tableNames.stream()
                .filter(name -> !name.contains(FLYWAY))
                .map(TRUNCATE::concat)
                .forEach(truncationDMLs::add);
    }
}
