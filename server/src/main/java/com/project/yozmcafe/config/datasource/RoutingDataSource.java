package com.project.yozmcafe.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class RoutingDataSource extends AbstractRoutingDataSource {

    public static final String MASTER_DATA_SOURCE = "master";
    public static final String SLAVE_DATA_SOURCE = "slave";

    @Override
    protected Object determineCurrentLookupKey() {
        boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if (isReadOnly) {
            return SLAVE_DATA_SOURCE;
        }
        return MASTER_DATA_SOURCE;
    }
}
