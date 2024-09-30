package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import com.example.databaseCopyAndAnalyze.sourceDatabase.model.ExamEntityJSON;
import jakarta.persistence.*;

import javax.annotation.processing.Generated;
import java.time.LocalDate;
import java.util.List;

@Entity
public class ExamEntity {
    @Id
    @GeneratedValue
    private Long id;
    private Long idInSource;
    @ManyToOne
    private ModuleEntity module;
    @ManyToOne
    private UserEntity mentor;
    @ManyToOne
    private UserEntity student;
    private LocalDate date;
    private boolean cancelled;
    private boolean success;
    private String comment;
    @OneToMany ( fetch = FetchType.EAGER)
    private List<ResultEntity> results;

    public void setModule(ModuleEntity module) {
        this.module = module;
    }

    public void setMentor(UserEntity mentor) {
        this.mentor = mentor;
    }

    public void setUser(UserEntity user, String userType){
        if(userType.equals("mentor")){
            setMentor(user);
        } else if( userType.equals("student")){
            setStudent(user);
        }
    }

    public void setStudent(UserEntity student) {
        this.student = student;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setBooleanField(boolean value, String fieldName){
        if (fieldName.equals("cancelled")) {
            setCancelled(value);
        } else if(fieldName.equals("success")){
            setSuccess(value);
        }
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setResults(List<ResultEntity> results) {
        this.results = results;
    }

    public ModuleEntity getModule() {
        return module;
    }

    public UserEntity getMentor() {
        return mentor;
    }

    public LocalDate getDate() {
        return date;
    }

    public UserEntity getStudent() {
        return student;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public boolean isSuccess() {
        return success;
    }

    public String getComment() {
        return comment;
    }

    public List<ResultEntity> getResults() {
        return results;
    }

    @Override
    public String toString() {
        return "ExamEntity{" +
                "results=" + results.toString() +
                '}';
    }

    public void setIdInSource(Long idInSource) {
        this.idInSource = idInSource;
    }

    public boolean equalsWithExamFromSource(ExamEntityJSON examEntityJSON){
        if(idInSource.equals(examEntityJSON.getId())){
            return true;
        }
        return false;
    }
}
