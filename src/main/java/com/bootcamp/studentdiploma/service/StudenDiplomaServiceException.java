package com.bootcamp.studentdiploma.service;

public class StudenDiplomaServiceException extends Exception {
    public StudenDiplomaServiceException(StudentDiplomaServiceError error, Object ... args) {
        super(error.getMessage(args));
    }

    public StudenDiplomaServiceException(StudentDiplomaServiceError error) {
        super(error.getMessage());
    }
}
