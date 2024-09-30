package com.example.databaseCopyAndAnalyze.targetDatabase.repository;

import com.example.databaseCopyAndAnalyze.targetDatabase.model.ResultEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResultRepository extends JpaRepository<ResultEntity, Long> {
}
