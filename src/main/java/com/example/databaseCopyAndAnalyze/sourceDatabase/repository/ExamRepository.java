package com.example.databaseCopyAndAnalyze.sourceDatabase.repository;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.ExamEntityJSON;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExamRepository extends JpaRepository<ExamEntityJSON, Long> {
    List<ExamEntityJSON> findAll();
}
