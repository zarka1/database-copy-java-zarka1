package com.example.databaseCopyAndAnalyze.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

@Configuration
public class SourceDatasourceConfiguration {

    @ConfigurationProperties("spring.datasource.source")
    @Bean
    public DataSourceProperties sourceDatasourceProperties(){
        return new DataSourceProperties();
    }

    @Bean
    public DataSource sourceDataSource(){

        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUsername(sourceDatasourceProperties().getUsername());
        dataSource.setPassword(sourceDatasourceProperties().getPassword());
        dataSource.setUrl(sourceDatasourceProperties().getUrl());
        dataSource.setDriverClassName(sourceDatasourceProperties().getDriverClassName());
        return dataSource;
    }
}
