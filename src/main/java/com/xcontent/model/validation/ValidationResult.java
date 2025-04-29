package com.xcontent.model.validation;

import java.util.ArrayList;
import java.util.List;

public class ValidationResult {
    private final List<String> errors;

    public ValidationResult() {
        this.errors = new ArrayList<>();
    }

    public void addError(String error) {
        errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }

    public List<String> getErrors() {
        return new ArrayList<>(errors);
    }

    public String getErrorMessage() {
        return String.join("\n", errors);
    }
} 