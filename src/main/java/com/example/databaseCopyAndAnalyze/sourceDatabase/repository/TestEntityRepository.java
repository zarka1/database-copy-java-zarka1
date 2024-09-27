package com.example.databaseCopyAndAnalyze.sourceDatabase.repository;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.TestEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestEntityRepository extends JpaRepository<TestEntity, Long> {
}
