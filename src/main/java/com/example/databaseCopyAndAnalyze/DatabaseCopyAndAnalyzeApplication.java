package com.example.databaseCopyAndAnalyze;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.TestEntity;
import com.example.databaseCopyAndAnalyze.sourceDatabase.repository.TestEntityRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.UserEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.jdbc.core.JdbcTemplate;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.databaseCopyAndAnalyze.targetDatabase.model"}
)
public class DatabaseCopyAndAnalyzeApplication implements CommandLineRunner{

    @Autowired
    private UserEntityRepository userEntityRepository;
    @Autowired
    private TestEntityRepository testEntityRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public static void main(String[] args) {
        SpringApplication.run(DatabaseCopyAndAnalyzeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Kopasz");
        TestEntity testEntity = new TestEntity();
        testEntity.setName("Bela");
        userEntityRepository.save(userEntity);
        testEntityRepository.save(testEntity);
        jdbcTemplate.execute("INSERT INTO  usertypeentity (ID, NAME) VALUES (1, 'MENTOR')");
        jdbcTemplate.execute("INSERT INTO usertypeentity (ID, NAME) VALUES (2, 'STUDENT')");
        jdbcTemplate.execute("INSERT INTO  moduleentity (ID, NAME) VALUES (1, 'PROGRAMMING_BASICS')");
        jdbcTemplate.execute("INSERT INTO moduleentity (ID, NAME) VALUES (2, 'WEB')");
        jdbcTemplate.execute("INSERT INTO  moduleentity (ID, NAME) VALUES (3, 'OOP')");
        jdbcTemplate.execute("INSERT INTO moduleentity (ID, NAME) VALUES (4, 'ADVANCED')");
    }
}
