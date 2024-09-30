package com.example.databaseCopyAndAnalyze.targetDatabase.controller;

import com.example.databaseCopyAndAnalyze.targetDatabase.service.TargetExamServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class StudentRiportController {

    private TargetExamServiceImp studentServiceImp;

    public StudentRiportController(TargetExamServiceImp studentServiceImp) {
        this.studentServiceImp = studentServiceImp;
    }

    @GetMapping("api/copydata")
    public ResponseEntity<?> copydata() throws IOException {
        studentServiceImp.copySourceToTarget();
        return ok("Data copied.");
    }


}
