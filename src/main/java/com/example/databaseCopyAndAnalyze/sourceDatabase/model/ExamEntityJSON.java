package com.example.databaseCopyAndAnalyze.sourceDatabase.model;

import com.fasterxml.jackson.databind.JsonNode;
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
    private Map<String, JsonNode> attributes;

    public void setAttributes(Map<String, JsonNode> attributes) {
        this.attributes = attributes;
    }

    @Override
    public String toString() {
        return "ExamEntityJSON{" +
                "id=" + id +
                ", attributes=" + attributes +
                '}';
    }

    public Map<String, JsonNode> getAttributes(){
        return this.attributes;
    }

    public Long getId() {
        return id;
    }
}
