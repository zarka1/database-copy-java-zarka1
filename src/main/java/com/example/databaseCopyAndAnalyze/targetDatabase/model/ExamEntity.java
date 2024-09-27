package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
public class ExamEntity {
    @Id
    private Long id;
    @ManyToOne
    private ModuleEntity module;
    @ManyToMany
    private List<UserEntity> mentors;
    @ManyToOne
    private UserEntity student;
    private LocalDate date;
    private boolean cancelled;
    private boolean success;
    private String comment;
    @OneToMany
    private List<ResultEntity> results;
}
