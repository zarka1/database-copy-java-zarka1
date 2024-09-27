package com.example.databaseCopyAndAnalyze.sourceDatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class TestEntity {
    @GeneratedValue
    @Id
    private Long id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }
}
