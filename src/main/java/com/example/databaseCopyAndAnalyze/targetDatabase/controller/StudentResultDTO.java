package com.example.databaseCopyAndAnalyze.targetDatabase.controller;

import java.util.Map;

public record StudentResultDTO(String email, Map<String, Double> results) {
}
