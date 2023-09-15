package com.project.yozmcafe.config.datasource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.LazyConnectionDataSourceProxy;

import javax.sql.DataSource;
import java.util.Map;

@Configuration
public class DataSourceConfig {

    public static final String MASTER_DATA_SOURCE = "master";
    public static final String SLAVE_DATA_SOURCE = "slave";
    private static final String ROUTING_DATA_SOURCE = "routingDataSource";
    private static final String MASTER_DATA_SOURCE_LOCATION = "spring.datasource.master";
    private static final String SLAVE_DATA_SOURCE_LOCATION = "spring.datasource.slave";

    @Bean
    @Qualifier(MASTER_DATA_SOURCE)
    @ConfigurationProperties(prefix = MASTER_DATA_SOURCE_LOCATION)
    public DataSource masterDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Qualifier(SLAVE_DATA_SOURCE)
    @ConfigurationProperties(prefix = SLAVE_DATA_SOURCE_LOCATION)
    public DataSource slaveDataSource() {
        return DataSourceBuilder.create()
                .build();
    }

    @Bean
    @Qualifier(ROUTING_DATA_SOURCE)
    public DataSource routingDataSource(@Qualifier(MASTER_DATA_SOURCE) DataSource sourceDataSource,
                                        @Qualifier(SLAVE_DATA_SOURCE) DataSource replicaDataSource) {
        final RoutingDataSource routingDataSource = new RoutingDataSource();
        final Map<Object, Object> dataSources = Map.of(MASTER_DATA_SOURCE, sourceDataSource,
                SLAVE_DATA_SOURCE, replicaDataSource);

        routingDataSource.setDefaultTargetDataSource(dataSources.get(MASTER_DATA_SOURCE));
        routingDataSource.setTargetDataSources(dataSources);

        return routingDataSource;
    }

    @Bean
    @Primary
    public DataSource dataSource(
            @Qualifier(ROUTING_DATA_SOURCE) DataSource routingDataSource) {
        return new LazyConnectionDataSourceProxy(routingDataSource);
    }
}
