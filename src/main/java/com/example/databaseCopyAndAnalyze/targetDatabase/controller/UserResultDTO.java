package com.example.databaseCopyAndAnalyze.targetDatabase.controller;

import java.util.Map;

public record UserResultDTO(String email, Map<String, Double> results) {
}
