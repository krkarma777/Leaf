package com.leaf.web.utils;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;
import java.util.stream.Collectors;

public class ValidationErrorHandler {

    public static ResponseEntity<Map<String, Object>> handleValidationErrors(BindingResult bindingResult) {
        Map<String, String> errors = getErrors(bindingResult);
        String errorMessage = errors.values().stream().findFirst().orElse("Validation error");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(Map.of("message", errorMessage, "errors", errors));
    }

    private static Map<String, String> getErrors(BindingResult bindingResult) {
        return bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));
    }
}
