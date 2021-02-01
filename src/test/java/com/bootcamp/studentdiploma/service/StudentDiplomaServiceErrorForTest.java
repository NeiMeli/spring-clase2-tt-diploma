package com.bootcamp.studentdiploma.service;

public enum StudentDiplomaServiceErrorForTest implements StudentDiplomaServiceError {
    ERROR;

    @Override
    public String getMessage() {
        return "ERROR";
    }

    @Override
    public String getMessage(Object... args) {
        return getMessage();
    }
}
