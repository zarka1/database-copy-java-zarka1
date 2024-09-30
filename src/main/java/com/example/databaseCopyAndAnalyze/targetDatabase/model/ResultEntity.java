package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.*;

@Entity
public class ResultEntity {
    @Id
    @GeneratedValue
    private Long id;
    private String dimension;
    private int result;

    public void setDimension(String dimension) {
        this.dimension = dimension;
    }

    public void setResult(int result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "ResultEntity{" +
                "dimension='" + dimension + '\'' +
                ", result=" + result +
                '}';
    }

    public String getDimension() {
        return dimension;
    }

    public int getResult() {
        return result;
    }
}
