package com.example.databaseCopyAndAnalyze.sourceDatabase.repository;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.ExamEntityJSON;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamEntityRepository extends JpaRepository<ExamEntityJSON, Long> {
}
