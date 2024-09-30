package com.example.databaseCopyAndAnalyze.targetDatabase.service;

import com.example.databaseCopyAndAnalyze.targetDatabase.repository.ExamRepository;
import org.springframework.stereotype.Service;

@Service
public class ExamServiceImp {
    private final com.example.databaseCopyAndAnalyze.sourceDatabase.service.ExamServiceImp sourceExamRepository;
    private final ExamRepository targetExamRepository;

    public ExamServiceImp(com.example.databaseCopyAndAnalyze.sourceDatabase.service.ExamServiceImp sourceExamRepository, ExamRepository targetExamRepository) {
        this.sourceExamRepository = sourceExamRepository;
        this.targetExamRepository = targetExamRepository;
    }

    public void copySourceToTarget(){

    }
}
