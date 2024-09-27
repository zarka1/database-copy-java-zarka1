package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class ResultEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String dimension;
    private byte result;
}
