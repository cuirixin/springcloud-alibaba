package com.phoenix.config.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class DataSourceConfig {
    // @Slf4j 注解代替
    private static final Logger log = LoggerFactory.getLogger(DataSourceConfig.class);

    @Value("${druid.type}")
    public Class<? extends DataSource> dataSourceType;

    @Bean(name = "masterDataSource")
    @Primary // 优先注入
    @ConfigurationProperties(prefix = "druid.master")
    public DataSource masterDataSource() {
        DataSource masterDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        log.info("<------MASTER------>" + masterDataSource);
        return masterDataSource;
    }

    @Bean(name = "slaveDataSource")
    @ConfigurationProperties(prefix = "druid.slave")
    public DataSource slaveDataSource() {
        DataSource slaveDataSource = DataSourceBuilder.create().type(dataSourceType).build();
        log.info("<------SLAVE------>" + slaveDataSource);
        return slaveDataSource;
    }


    @Bean
    public DataSource routingDataSource(@Qualifier("masterDataSource") DataSource masterDataSource,
                                        @Qualifier("slaveDataSource") DataSource slaveDataSource) {
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put(DataBaseContextHolder.DataBaseType.MASTER, masterDataSource);
        targetDataSources.put(DataBaseContextHolder.DataBaseType.SLAVE, slaveDataSource);
        ReadWriteSplitRoutingDataSource routingDataSource = new ReadWriteSplitRoutingDataSource();
        routingDataSource.setDefaultTargetDataSource(masterDataSource);
        routingDataSource.setTargetDataSources(targetDataSources);
        return routingDataSource;
    }


}
