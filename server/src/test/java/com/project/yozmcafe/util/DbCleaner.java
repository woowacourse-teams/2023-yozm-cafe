package com.project.yozmcafe.util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DbCleaner {

    private static final int OFF = 0;
    private static final int ON = 0;
    private static final String FOREIGN_KEY_CHECKS = "SET FOREIGN_KEY_CHECKS = ";
    private static final String TRUNCATE_TABLE = "TRUNCATE TABLE ";
    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void dbClear() {
        setForeignKeyCheck(OFF);
        truncateAllTable();
        setForeignKeyCheck(ON);
    }

    private void setForeignKeyCheck(final int enabled) {
        em.createNativeQuery(FOREIGN_KEY_CHECKS + enabled).executeUpdate();
    }

    private void truncateAllTable() {
        final List<String> dbTableName = findDbTableName();
        for (final String tableName : dbTableName) {
            em.createNativeQuery(TRUNCATE_TABLE + tableName).executeUpdate();
        }
    }

    private List<String> findDbTableName() {
        return em.createNativeQuery("SHOW TABLES").getResultList();
    }
}
