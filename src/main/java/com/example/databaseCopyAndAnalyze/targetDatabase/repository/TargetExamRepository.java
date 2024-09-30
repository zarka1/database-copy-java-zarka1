package com.example.databaseCopyAndAnalyze.targetDatabase.repository;


import com.example.databaseCopyAndAnalyze.targetDatabase.model.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<ExamEntity, Long> {
}
