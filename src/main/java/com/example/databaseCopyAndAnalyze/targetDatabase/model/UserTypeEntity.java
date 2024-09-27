package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class UserTypeEntity {
    @Id
    private Long id;
    private String name;
}
