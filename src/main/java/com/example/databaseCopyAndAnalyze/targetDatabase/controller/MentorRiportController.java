package com.example.databaseCopyAndAnalyze.targetDatabase.controller;

import com.example.databaseCopyAndAnalyze.targetDatabase.service.UserServiceImp;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class MentorRiportController {


    private final UserServiceImp userServiceImp;

    public MentorRiportController(UserServiceImp userServiceImp) {
        this.userServiceImp = userServiceImp;
    }

    @GetMapping("api/getmentorresults")
    public ResponseEntity<?> getMentorResults(@RequestParam(defaultValue = "0") String ratiostring) {
        double ratio;
        try {
            ratio = Double.parseDouble(ratiostring);
        } catch (NumberFormatException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body("Ratio is not a number.");
        }
        List<String> results = userServiceImp.getMentorResults(ratio);
        return ResponseEntity.ok(results);
    }
}
