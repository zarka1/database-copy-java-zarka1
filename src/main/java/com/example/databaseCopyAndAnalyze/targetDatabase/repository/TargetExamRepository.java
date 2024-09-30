package com.example.databaseCopyAndAnalyze.targetDatabase.repository;


import com.example.databaseCopyAndAnalyze.targetDatabase.model.ExamEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TargetExamRepository extends JpaRepository<ExamEntity, Long> {
}
