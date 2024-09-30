package com.example.databaseCopyAndAnalyze;

import com.example.databaseCopyAndAnalyze.sourceDatabase.repository.ExamRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserTypeEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.UserRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.UserTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.List;

@SpringBootApplication
@EntityScan(basePackages = {"com.example.databaseCopyAndAnalyze.targetDatabase.model"}
)
public class DatabaseCopyAndAnalyzeApplication implements CommandLineRunner{

    @Autowired
    private UserRepository userEntityRepository;
    @Autowired
    private UserTypeRepository userTypeRepository;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    ExamRepository examEntityRepository;
    public static void main(String[] args) {
        SpringApplication.run(DatabaseCopyAndAnalyzeApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        /*jdbcTemplate.execute("INSERT INTO  usertypeentity (ID, NAME) VALUES (1, '\"mentor\"')");
        jdbcTemplate.execute("INSERT INTO usertypeentity (ID, NAME) VALUES (2, '\"student\"')");
        jdbcTemplate.execute("INSERT INTO  moduleentity (ID, NAME) VALUES (1, '\"ProgBasics\"')");
        jdbcTemplate.execute("INSERT INTO moduleentity (ID, NAME) VALUES (2, '\"Web\"')");
        jdbcTemplate.execute("INSERT INTO  moduleentity (ID, NAME) VALUES (3, '\"OOP\"')");
        jdbcTemplate.execute("INSERT INTO moduleentity (ID, NAME) VALUES (4, '\"Advanced\"')");
        List<UserTypeEntity> userTypeEntities = userTypeRepository.findAll();
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Balazs");
        userEntity.setDob(LocalDate.of(1975, 10, 16));
        userEntity.setEmail("\"szucsbalazs@gmail.com\"");
        userEntity.setUserType(userTypeEntities.get(1));
        userEntityRepository.save(userEntity);
        UserEntity userEntity2 = new UserEntity();
        userEntity2.setName("Foo");
        userEntity2.setDob(LocalDate.of(1999, 10, 16));
        userEntity2.setEmail("\"foo@bar.com\"");
        userEntity2.setUserType(userTypeEntities.get(1));
        userEntityRepository.save(userEntity2);
        UserEntity userEntity3 = new UserEntity();
        userEntity3.setName("Fabian Mano");
        userEntity3.setDob(LocalDate.of(1989, 10, 16));
        userEntity3.setEmail("\"mano.fabian@codecool.com\"");
        userEntity3.setUserType(userTypeEntities.get(0));
        userEntityRepository.save(userEntity3);
        UserEntity userEntity4 = new UserEntity();
        userEntity4.setName("Szarka Peter");
        userEntity4.setDob(LocalDate.of(1995, 10, 16));
        userEntity4.setEmail("\"peter.szarka@codecool.com\"");
        userEntity4.setUserType(userTypeEntities.get(0));
        userEntityRepository.save(userEntity4);*/
    }
}
