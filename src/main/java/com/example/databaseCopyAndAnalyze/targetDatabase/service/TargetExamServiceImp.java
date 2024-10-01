package com.example.databaseCopyAndAnalyze.targetDatabase.service;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.ExamEntityJSON;
import com.example.databaseCopyAndAnalyze.sourceDatabase.repository.ExamRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.*;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.*;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TargetExamServiceImp {
    private final ExamRepository sourceExamRepository;
    private final TargetExamRepository targetExamRepository;
    private final UserTypeRepository userTypeRepository;
    private final ModuleRepository moduleRepository;
    private final UserRepository userRepository;
    private final ResultRepository resultRepository;


    public TargetExamServiceImp(ExamRepository sourceExamRepository, TargetExamRepository targetExamRepository, UserRepository userEntityRepository, UserTypeRepository userTypeRepository, ModuleRepository moduleRepository, UserRepository userRepository, ResultRepository resultRepository) {
        this.sourceExamRepository = sourceExamRepository;
        this.targetExamRepository = targetExamRepository;
        this.userTypeRepository = userTypeRepository;
        this.moduleRepository = moduleRepository;
        this.userRepository = userRepository;
        this.resultRepository = resultRepository;
    }

    public void copySourceToTarget() {
        List<ExamEntityJSON> examsNotValid = new ArrayList<>();
        List<UserTypeEntity> userTypes = userTypeRepository.findAll();
        List<ExamEntityJSON> jsonExams = sourceExamRepository.findAll();
        List<ModuleEntity> modules = moduleRepository.findAll();
        List<UserEntity> users = userRepository.findAll();
        List<ExamEntity> exams = targetExamRepository.findAll();
        for (ExamEntityJSON exam : jsonExams) {
            boolean alreadyExist = false;
            for(ExamEntity targetExam : exams) {
                if(targetExam.equalsWithExamFromSource(exam)){
                    alreadyExist = true;
                    break;
                }
            }
            if(alreadyExist){
                continue;
            }
            saveExamToRepo(examsNotValid, userTypes, modules, users, exam);
        }
    }
    @Transactional
    public void saveExamToRepo(List<ExamEntityJSON> examsNotValid, List<UserTypeEntity> userTypes, List<ModuleEntity> modules, List<UserEntity> users, ExamEntityJSON exam) {
        ExamEntity examEntity = new ExamEntity();
        targetExamRepository.save(examEntity);
        setModule(examsNotValid, modules, exam, examEntity);
        setUser(examsNotValid, userTypes, users, exam, examEntity, "mentor", 0);
        setUser(examsNotValid, userTypes, users, exam, examEntity, "student", 1);
        setDate(examsNotValid, exam, examEntity);
        setBooleanFields(examsNotValid, exam, examEntity, "cancelled");
        setBooleanFields(examsNotValid, exam, examEntity, "success");
        setComment(examsNotValid, exam, examEntity);
        setResults(examsNotValid, exam, examEntity);
        examEntity.setIdInSource(exam.getId());
        targetExamRepository.save(examEntity);
    }

    private void setUser(List<ExamEntityJSON> examsNotValid,
                         List<UserTypeEntity> userTypes,
                         List<UserEntity> users,
                         ExamEntityJSON exam,
                         ExamEntity examEntity,
                         String userType,
                         int numberOfElementInUserTypes) {
        if (!containsValidUser(users, exam, userTypes.get(numberOfElementInUserTypes), userType).isEmpty()) {
            examEntity.setUser(containsValidUser(users, exam, userTypes.get(numberOfElementInUserTypes), userType).get(), userType);
        } else {
            examsNotValid.add(exam);
        }
    }

    private void setModule(List<ExamEntityJSON> examsNotValid, List<ModuleEntity> modules, ExamEntityJSON exam, ExamEntity examEntity) {
        if (containsValidModule(modules, exam).isPresent()) {
            examEntity.setModule(containsValidModule(modules, exam).get());
        } else {
            examsNotValid.add(exam);
        }
    }

    private void setDate(List<ExamEntityJSON> examsNotValid, ExamEntityJSON exam, ExamEntity examEntity) {
        if (containsValidDate(exam).isPresent()) {
            examEntity.setDate(containsValidDate(exam).get());
        } else {
            examsNotValid.add(exam);
        }
    }

    private Optional<ModuleEntity> containsValidModule(List<ModuleEntity> modules, ExamEntityJSON exam) {
        if (!exam.getAttributes().containsKey("module")) {
            return Optional.empty();
        }
        for (ModuleEntity module : modules) {
            if (module.getName().equals(exam.getAttributes().get("module").toString())) {
                return Optional.of(module);
            }
        }
        return Optional.empty();
    }

    private Optional<UserEntity> containsValidUser(List<UserEntity> users, ExamEntityJSON exam, UserTypeEntity type, String userType) {
        if (!exam.getAttributes().containsKey(userType)) {
            return Optional.empty();
        }
        for (UserEntity user : users) {
            if (user.getEmail().equals(exam.getAttributes().get(userType).toString()) &&
                    user.getUserType().equals(type)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    private Optional<LocalDate> containsValidDate(ExamEntityJSON exam) {
        if (!exam.getAttributes().containsKey("date")) {
            return Optional.empty();
        }
        try {
            LocalDate date = LocalDate.parse(exam.getAttributes().get("date").toString().substring(1,
                    exam.getAttributes().get("date").toString().length() - 1));
            return Optional.of(date);
        } catch (DateTimeParseException e) {
            return Optional.empty();
        }
    }

    private void setBooleanFields(List<ExamEntityJSON> examsNotValid,
                                  ExamEntityJSON exam,
                                  ExamEntity examEntity,
                                  String booleanField) {
        if (containsValidBooleanField( exam, booleanField).isPresent()) {
            examEntity.setBooleanField(containsValidBooleanField( exam, booleanField).get(), booleanField);
        } else {
            examsNotValid.add(exam);
        }
    }

    private Optional<Boolean> containsValidBooleanField(ExamEntityJSON exam, String booleanField) {
        if (!exam.getAttributes().containsKey(booleanField)) {
            return Optional.empty();
        }
        String fieldValue = exam.getAttributes().get(booleanField).toString();
        if (!fieldValue.equals("true") && !fieldValue.equals("false")) {
            return Optional.empty();
        }
        boolean field = Boolean.parseBoolean(exam.getAttributes().get(booleanField).toString());
        return Optional.of(field);
    }

    private void setComment(List<ExamEntityJSON> examsNotValid,
                                  ExamEntityJSON exam,
                                  ExamEntity examEntity) {
        if (exam.getAttributes().containsKey("comment")) {
            examEntity.setComment(exam.getAttributes().get("comment").toString());

        } else {
            examsNotValid.add(exam);
        }
    }

    private Optional<List<ResultEntity>> containsValidResults(List<ExamEntityJSON> examsNotValid, ExamEntityJSON exam, ExamEntity examEntity) throws IOException {
        if (!exam.getAttributes().containsKey("results")) {
            return Optional.empty();
        }
        List<ResultEntity> results = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = "{\"results\":" + exam.getAttributes().get("results") + "}";
        JsonNode rootNode;
        rootNode= objectMapper.readTree(jsonString);
        JsonNode resultsNode = rootNode.path("results");
        for (JsonNode resultNode : resultsNode) {
            saveValidResultsToRepo(examsNotValid, exam, results, resultNode, examEntity);
        }
        if(results.size() != 0){
            return Optional.of(results);
        }
        else return Optional.empty();
    }

    private void saveValidResultsToRepo(List<ExamEntityJSON> examsNotValid, ExamEntityJSON exam, List<ResultEntity> results, JsonNode resultNode, ExamEntity examEntity) {
        ResultEntity resultEntity = new ResultEntity();
        if(!Objects.equals(resultNode.path("dimension").asText(), "")) {
            resultEntity.setDimension(resultNode.path("dimension").asText());
        } else {
            examsNotValid.add(exam);
            return;
        }
        int result = resultNode.path("result").asInt();
        if (result >= 0 && result <= 100){
            resultEntity.setResult(result);
        } else {
            examsNotValid.add(exam);
            return;
        }
        results.add(resultEntity);
        resultRepository.saveAndFlush(resultEntity);
    }

    private void setResults(List<ExamEntityJSON> examsNotValid, ExamEntityJSON exam, ExamEntity examEntity){
        try{
            if(containsValidResults(examsNotValid, exam, examEntity).isPresent()){
                examEntity.setResults(containsValidResults(examsNotValid, exam, examEntity).get());
            }
        } catch(IOException e){
            examsNotValid.add(exam);
        }
    }
}
