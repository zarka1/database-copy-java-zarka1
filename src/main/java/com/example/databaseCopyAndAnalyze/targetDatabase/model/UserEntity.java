package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class UserEntity {
    @GeneratedValue
    @Id
    private Long id;
    private String name;
    private LocalDate dob;
    @ManyToOne
    private UserTypeEntity userType;
    @Column(unique = true)
    private String email;

    public void setName(String name) {
        this.name = name;
    }
}
