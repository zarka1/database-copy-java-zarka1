package com.example.databaseCopyAndAnalyze.sourceDatabase.service;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.ExamEntityJSON;
import com.example.databaseCopyAndAnalyze.sourceDatabase.repository.ExamRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class ExamServiceImp {
    private final ExamRepository examRepository;

    public ExamServiceImp(ExamRepository examRepository) {
        this.examRepository = examRepository;
    }

    public void createExam(String examJSON) throws JsonProcessingException {
        ExamEntityJSON examEntityJSON = new ExamEntityJSON();
        ObjectMapper mapper = new ObjectMapper();
        Map<String, JsonNode> json = mapper.readValue(examJSON,
                new TypeReference<>() {});
        examEntityJSON.setAttributes(json);
        examRepository.save(examEntityJSON);
    }
}
