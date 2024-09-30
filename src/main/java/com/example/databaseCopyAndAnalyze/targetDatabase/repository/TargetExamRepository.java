package com.example.databaseCopyAndAnalyze.targetDatabase.repository;


import com.example.databaseCopyAndAnalyze.targetDatabase.model.ExamEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.ModuleEntity;
import com.example.databaseCopyAndAnalyze.targetDatabase.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.web.PageableDefault;

import java.util.List;

public interface TargetExamRepository extends JpaRepository<ExamEntity, Long> {
    List<ExamEntity> findByModuleAndStudentAndCancelledIsFalse(ModuleEntity module, UserEntity student);
    List<ExamEntity> findByMentor(UserEntity mentor);
}
