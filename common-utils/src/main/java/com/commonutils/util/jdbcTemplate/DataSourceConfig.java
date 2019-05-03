package com.commonutils.util.jdbcTemplate;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DataSourceConfig {

    @Bean(name = "sysQryCenter")
    public QryCenter sysQryCenter(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        return new QryCenter(dataSource);
    }

}
