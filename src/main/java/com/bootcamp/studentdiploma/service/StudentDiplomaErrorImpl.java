package com.bootcamp.studentdiploma.service;

public enum StudentDiplomaErrorImpl implements StudentDiplomaServiceError {
    INVALID_GRADE_VALUE ("Valor de nota inválido: %s"),
    NO_GRADES ("Lista de notas vacía"),
    NOT_APPROVED("Alumno reprobado: %s de promedio");

    private final String message;

    StudentDiplomaErrorImpl(String s) {
        this.message = s;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public String getMessage(Object... args) {
        return String.format(message, args);
    }
}
