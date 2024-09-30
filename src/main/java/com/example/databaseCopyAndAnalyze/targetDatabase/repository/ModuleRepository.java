package com.example.databaseCopyAndAnalyze.targetDatabase.repository;


import com.example.databaseCopyAndAnalyze.targetDatabase.model.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<ModuleEntity, Long> {
}
