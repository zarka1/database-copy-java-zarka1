package com.example.databaseCopyAndAnalyze.sourceDatabase.controller;

import com.example.databaseCopyAndAnalyze.sourceDatabase.service.ExamServiceImp;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExamController {

    private ExamServiceImp examServiceImp;

    public ExamController(ExamServiceImp examServiceImp) {
        this.examServiceImp = examServiceImp;
    }

    @PostMapping("api/addexam")
    public ResponseEntity<Void> addNewExam(@RequestBody String examJson) throws JsonProcessingException {
        examServiceImp.createExam(examJson);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
