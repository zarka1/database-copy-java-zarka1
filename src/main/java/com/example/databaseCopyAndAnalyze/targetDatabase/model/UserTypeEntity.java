package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class UserTypeEntity {
    @Id
    private Long id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserTypeEntity userType)) return false;
        return Objects.equals(id, userType.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


