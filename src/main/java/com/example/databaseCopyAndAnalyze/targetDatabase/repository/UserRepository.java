package com.example.databaseCopyAndAnalyze.targetDatabase.repository;


import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
