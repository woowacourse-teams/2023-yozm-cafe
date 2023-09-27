package com.project.yozmcafe.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import static com.project.yozmcafe.config.datasource.DataSourceConfig.MASTER_DATA_SOURCE;
import static com.project.yozmcafe.config.datasource.DataSourceConfig.SLAVE_DATA_SOURCE;

public class RoutingDataSource extends AbstractRoutingDataSource {
    @Override
    protected Object determineCurrentLookupKey() {
        final boolean isReadOnly = TransactionSynchronizationManager.isCurrentTransactionReadOnly();
        if(isReadOnly){
            return SLAVE_DATA_SOURCE;
        }
        return MASTER_DATA_SOURCE;
    }
}
