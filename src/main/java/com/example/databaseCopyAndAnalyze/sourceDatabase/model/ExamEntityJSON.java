package com.example.databaseCopyAndAnalyze.sourceDatabase.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.HashMap;
import java.util.Map;

@Entity
public class ExamEntityJSON {
    @Id
    @GeneratedValue
    private Long id;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, String> attributes;

    public void setAttributes(Map<String, String> attributes) {
        this.attributes = attributes;
    }
}
