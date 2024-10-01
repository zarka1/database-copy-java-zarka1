package com.example.databaseCopyAndAnalyze.targetDatabase.service;

import com.example.databaseCopyAndAnalyze.targetDatabase.controller.StudentResultDTO;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.ExamEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.ModuleEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.ResultEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.ModuleRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.ResultRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.TargetExamRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp {
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final TargetExamRepository examRepository;
    private final ResultRepository resultRepository;

    public UserServiceImp(UserRepository userRepository, ModuleRepository moduleRepository, TargetExamRepository examRepository, ResultRepository resultRepository) {
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.examRepository = examRepository;
        this.resultRepository = resultRepository;
    }

    public List<StudentResultDTO> getStudentResults(){
        List<StudentResultDTO> results = new ArrayList<>();
        List<UserEntity> students = userRepository.findAllByUserTypeIdIs(2);
        List<ModuleEntity> modules = moduleRepository.findAll();
        for(UserEntity student : students){
            List<ExamEntity> examforStudentAndModule = new ArrayList<>();
            Map<String, Double> resultsMap = new HashMap<>();
            for(ModuleEntity module : modules){
                getTheLastExamForModul(student, examforStudentAndModule, module);
            }
            List<String> dimensions = new ArrayList<>();
            getAllDimensions(examforStudentAndModule, dimensions);
            for (String dimension : dimensions) {
                calculateResultForDimension(examforStudentAndModule, resultsMap, dimension);
            }
            StudentResultDTO studentResultDTO = new StudentResultDTO(student.getName(), resultsMap);
            results.add(studentResultDTO);
        }
        return results;
    }

    private void getTheLastExamForModul(UserEntity student, List<ExamEntity> examforStudentAndModule, ModuleEntity module) {
        List<ExamEntity> examsforStudentAndModule = new ArrayList<>();
        examsforStudentAndModule.addAll(examRepository
                .findByModuleAndStudentAndCancelledIsFalse(module, student));
        Collections.sort(examsforStudentAndModule, Comparator.comparing(ExamEntity::getDate));
        if(examsforStudentAndModule.size() > 0){
            examforStudentAndModule.add(examsforStudentAndModule.get(examsforStudentAndModule.size()-1));
        }
    }

    private static void getAllDimensions(List<ExamEntity> examforStudentAndModule, List<String> dimensions) {
        for(ExamEntity exam : examforStudentAndModule){
            for (ResultEntity result : exam.getResults()){
                if(!dimensions.contains(result.getDimension())){
                    dimensions.add(result.getDimension());
                }
            }
        }
    }

    private static void calculateResultForDimension(List<ExamEntity> examforStudentAndModule, Map<String, Double> resultsMap, String dimension) {
        int sumOfResults = 0;
        int numberOfResults = 0;
        for (ExamEntity exam : examforStudentAndModule) {
            for (ResultEntity result : exam.getResults()) {
                if (result.getDimension().equals(dimension)) {
                    sumOfResults += result.getResult();
                    numberOfResults++;
                }
            }
        }
        resultsMap.put(dimension, (double) sumOfResults / numberOfResults);
    }

    public List<String> getMentorResults(double ratio){
        List<String> results = new ArrayList<>();
        List<UserEntity> mentors = userRepository.findAllByUserTypeIdIs(1);
        List<ResultEntity> resultEntities = resultRepository.findAll();
        int sumOfResults = resultEntities.stream()
                .mapToInt(ResultEntity::getResult) // Convert to IntStream
                .sum();
        long numberOfEntities = resultEntities.stream()
                .count();
        double average = (double) sumOfResults / numberOfEntities;
        for (UserEntity mentor : mentors){
            saveMentorIfOutstanding(ratio, results, average, mentor);
        }
        return results;
    }

    private void saveMentorIfOutstanding(double ratio, List<String> results, double average, UserEntity mentor) {
        List<ExamEntity> examsForMentor = examRepository.findByMentor(mentor);
        int sumResults = 0;
        int numberOfResults = 0;
        for(ExamEntity examEntity : examsForMentor){
            for(ResultEntity result : examEntity.getResults()){
                sumResults += result.getResult();
                numberOfResults++;
            }
        }
        double averageOfMentor = sumResults / numberOfResults;
        if (ratio < 1 && averageOfMentor < average * ratio){
            results.add(mentor.getName());
        } else if(ratio > 1 && averageOfMentor > average * ratio){
            results.add(mentor.getName());
        }
    }
}
