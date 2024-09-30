package com.example.databaseCopyAndAnalyze.targetDatabase.repository;


import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByUserTypeIdIs(int id);
}
