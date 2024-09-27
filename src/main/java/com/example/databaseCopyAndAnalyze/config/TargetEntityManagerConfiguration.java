package com.example.databaseCopyAndAnalyze.config;


import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@EnableTransactionManagement
@Configuration
@EnableJpaRepositories(
        basePackages = "com.example.databaseCopyAndAnalyze.targetDatabase.repository",
        entityManagerFactoryRef = "targetEntityManagerFactoryBean",
        transactionManagerRef = "targetTransactionManager"
)
public class TargetEntityManagerConfiguration {


    @Bean
    LocalContainerEntityManagerFactoryBean targetEntityManagerFactoryBean(
            EntityManagerFactoryBuilder builder, @Qualifier("targetDataSource") DataSource dataSource){
        Map<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "create");
        return builder
                .dataSource(dataSource)
                .properties(properties)
                .packages("com.example.databaseCopyAndAnalyze.targetDatabase.model")
                .build();
    }

    @Bean
    PlatformTransactionManager targetTransactionManager(
            @Qualifier("targetEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean emfb){

        return new JpaTransactionManager(emfb.getObject());
    }
}
