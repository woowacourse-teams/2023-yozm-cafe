package com.project.yozmcafe;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

@Component
@Profile("test")
public class DataInitializer {

    private static final int OFF = 0;
    private static final int ON = 1;
    private static final int FIRST_COLUMN = 1;
    private static final String FLYWAY = "flyway";

    @Autowired
    private DataSource dataSource;
    @PersistenceContext
    private EntityManager em;

    private final List<String> deleteDMLs = new ArrayList<>();

    @BeforeEach
    @Transactional
    public void deleteAll() {
        if (deleteDMLs.isEmpty()) {
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
        deleteDMLs.stream()
                .map(em::createNativeQuery)
                .forEach(Query::executeUpdate);
    }

    private void init() {
        try (final Statement statement = dataSource.getConnection().createStatement()) {
            final ResultSet resultSet = statement.executeQuery("SHOW TABLES ");

            while (resultSet.next()) {
                final String tableName = resultSet.getString(FIRST_COLUMN);
                if (tableName.contains(FLYWAY)) {
                    continue;
                }

                deleteDMLs.add("TRUNCATE " + tableName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
