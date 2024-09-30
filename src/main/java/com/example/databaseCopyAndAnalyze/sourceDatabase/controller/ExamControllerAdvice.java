package com.example.databaseCopyAndAnalyze.sourceDatabase.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ExamControllerAdvice {

    @ResponseBody
    @ExceptionHandler(JsonProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String JSONProcessingExceptionHandler(JsonProcessingException ex)
    {
        return "JSON Processing Error: " + ex.getMessage();

    }
}
