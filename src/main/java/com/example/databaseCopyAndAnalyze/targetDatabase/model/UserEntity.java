package com.example.databaseCopyAndAnalyze.targetDatabase.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.Objects;

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

    public String getName() {
        return name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public UserTypeEntity getUserType() {
        return userType;
    }

    public void setUserType(UserTypeEntity userType) {
        this.userType = userType;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserEntity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dob=" + dob +
                ", userType=" + userType +
                ", email='" + email + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserEntity that)) return false;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name) && Objects.equals(dob, that.dob) && Objects.equals(userType, that.userType) && Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dob, userType, email);
    }
}
