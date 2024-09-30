package com.example.databaseCopyAndAnalyze.targetDatabase.controller;

import com.example.databaseCopyAndAnalyze.targetDatabase.service.TargetExamServiceImp;
import com.example.databaseCopyAndAnalyze.targetDatabase.service.UserServiceImp;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

import static org.springframework.http.ResponseEntity.ok;

@RestController
public class StudentRiportController {

    private TargetExamServiceImp examServiceImp;
    private final UserServiceImp userServiceImp;

    public StudentRiportController(TargetExamServiceImp studentServiceImp, UserServiceImp userServiceImp) {
        this.examServiceImp = studentServiceImp;
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("api/copydata")
    public ResponseEntity<?> copydata() throws IOException {
        examServiceImp.copySourceToTarget();
        return ok("Data copied.");
    }

    @GetMapping("api/getstudentresults")
    public ResponseEntity<?> getUserResults(){
        List<UserResultDTO> results = userServiceImp.getStudentResults();
        return ResponseEntity.ok(results);
    }


}
