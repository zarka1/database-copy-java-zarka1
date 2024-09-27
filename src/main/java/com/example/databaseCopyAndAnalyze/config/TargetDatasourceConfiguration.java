package com.example.databaseCopyAndAnalyze.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class TargetDatasourceConfiguration {

    @ConfigurationProperties("spring.datasource.target")
    @Bean
    public DataSourceProperties targetDatasourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    public DataSource targetDataSource() {

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(targetDatasourceProperties().getUsername());
        dataSource.setPassword(targetDatasourceProperties().getPassword());
        dataSource.setUrl(targetDatasourceProperties().getUrl());
        dataSource.setDriverClassName(targetDatasourceProperties().getDriverClassName());
        return dataSource;
    }
}
