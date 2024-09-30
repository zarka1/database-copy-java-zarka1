package com.example.databaseCopyAndAnalyze.targetDatabase.service;

import com.example.databaseCopyAndAnalyze.targetDatabase.controller.UserResultDTO;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.ExamEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.ModuleEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.ResultEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.ModuleRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.TargetExamRepository;
import com.example.databaseCopyAndAnalyze.targetDatabase.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImp {
    private final UserRepository userRepository;
    private final ModuleRepository moduleRepository;
    private final TargetExamRepository examRepository;

    public UserServiceImp(UserRepository userRepository, ModuleRepository moduleRepository, TargetExamRepository examRepository) {
        this.userRepository = userRepository;
        this.moduleRepository = moduleRepository;
        this.examRepository = examRepository;
    }

    public List<UserResultDTO> getStudentResults(){
        List<UserResultDTO> results = new ArrayList<>();
        List<UserEntity> students = userRepository.findAllByUserTypeIdIs(2);
        List<ModuleEntity> modules = moduleRepository.findAll();
        for(UserEntity student : students){
            List<ExamEntity> examforStudentAndModule = new ArrayList<>();
            Map<String, Double> resultsMap = new HashMap<>();
            for(ModuleEntity module : modules){
                List<ExamEntity> examsforStudentAndModule = new ArrayList<>();
                examsforStudentAndModule.addAll(examRepository
                        .findByModuleAndStudentAndCancelledIsFalse(module, student));
                Collections.sort(examsforStudentAndModule, Comparator.comparing(ExamEntity::getDate));
                if(examsforStudentAndModule.size() > 0){
                    examforStudentAndModule.add(examsforStudentAndModule.get(examsforStudentAndModule.size()-1));
                }
            }
            List<String> dimensions = new ArrayList<>();
            for(ExamEntity exam : examforStudentAndModule){
                System.out.println(exam.getModule());
                System.out.println(exam.getDate());
                System.out.println("examresults: " + exam.getResults());
            }
            for(ExamEntity exam : examforStudentAndModule){
                for (ResultEntity result : exam.getResults()){
                    if(!dimensions.contains(result.getDimension())){
                        dimensions.add(result.getDimension());
                    }
                }
            }
            for (String dimension : dimensions) {
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
            UserResultDTO userResultDTO = new UserResultDTO(student.getName(), resultsMap);
            results.add(userResultDTO);
        }
        return results;
    }
}
