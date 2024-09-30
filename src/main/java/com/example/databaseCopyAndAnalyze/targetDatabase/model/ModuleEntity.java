package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class ModuleEntity {
    @Id
    private Long id;
    @Column(unique = true)
    private String name;

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "ModuleEntity{" +
                "name='" + name + '\'' +
                '}';
    }
}
