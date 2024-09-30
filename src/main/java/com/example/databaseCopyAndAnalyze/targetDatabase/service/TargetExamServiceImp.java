package com.example.databaseCopyAndAnalyze.targetDatabase.service;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.ExamEntityJSON;
import com.example.databaseCopyAndAnalyze.sourceDatabase.repository.ExamRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.*;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.*;

import java.io.IOException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
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
            ExamEntity examEntity = new ExamEntity();
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
            System.out.println("mentor:" + examEntity.getMentor());
            System.out.println("student:" + examEntity.getStudent());
        } else {
            examsNotValid.add(exam);
        }
    }

    private void setModule(List<ExamEntityJSON> examsNotValid, List<ModuleEntity> modules, ExamEntityJSON exam, ExamEntity examEntity) {
        if (!containsValidModule(modules, exam).isEmpty()) {
            examEntity.setModule(containsValidModule(modules, exam).get());
            System.out.println(examEntity.getModule().toString());
        } else {
            examsNotValid.add(exam);
        }
    }

    private void setDate(List<ExamEntityJSON> examsNotValid, ExamEntityJSON exam, ExamEntity examEntity) {
        if (!containsValidDate(exam).isEmpty()) {
            examEntity.setDate(containsValidDate(exam).get());
            System.out.println(examEntity.getDate());
        }
    }

    private Optional<ModuleEntity> containsValidModule(List<ModuleEntity> modules, ExamEntityJSON exam) {
        if (!exam.getAttributes().containsKey("module")) {
            return Optional.empty();
        }
        for (ModuleEntity module : modules) {
            //System.out.println("from exam" + exam.getAttributes().get("module"));
            //System.out.println(module.getName());
            if (module.getName().toString().equals(exam.getAttributes().get("module").toString())) {
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
            /*System.out.println("from userentity:" + user.getEmail().toString());
            System.out.println("from examentity:" + exam.getAttributes().get(userType).toString());
            System.out.println(user.getEmail().toString().equals(exam.getAttributes().get(userType).toString()));*/
            if (user.getEmail().toString().equals(exam.getAttributes().get(userType).toString()) &&
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
        if (!containsValidBooleanField( exam, booleanField).isEmpty()) {
            examEntity.setBooleanField(containsValidBooleanField( exam, booleanField).get(), booleanField);
            System.out.println("cancelled: " + examEntity.isCancelled());
            System.out.println("success: " + examEntity.isSuccess());
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
            System.out.println(examEntity.getComment());
        } else {
            examsNotValid.add(exam);
        }
    }

    private Optional<List<ResultEntity>> containsValidResults(List<ExamEntityJSON> examsNotValid, ExamEntityJSON exam) throws IOException {
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
            ResultEntity resultEntity = new ResultEntity();
            if(resultNode.path("dimension").asText() != "") {
                resultEntity.setDimension(resultNode.path("dimension").asText());
            } else {
                examsNotValid.add(exam);
                continue;
            }
            int result = resultNode.path("result").asInt();
            if (result >= 0 && result <= 100){
                resultEntity.setResult(result);
            } else {
                examsNotValid.add(exam);
                continue;
            }
            results.add(resultEntity);
            resultRepository.save(resultEntity);
        }
        if(results.size() != 0){
            return Optional.of(results);
        }
        else return Optional.empty();
    }

    private void setResults(List<ExamEntityJSON> examsNotValid, ExamEntityJSON exam, ExamEntity examEntity){
        try{
            if(!containsValidResults(examsNotValid, exam).isEmpty()){
                examEntity.setResults(containsValidResults(examsNotValid, exam).get());
            }
        } catch(IOException e){
            examsNotValid.add(exam);
        }
    }
}
