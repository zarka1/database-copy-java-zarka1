package com.example.databaseCopyAndAnalyze.targetDatabase.repository;

import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserTypeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserTypeRepository extends JpaRepository<UserTypeEntity, Long> {
}
