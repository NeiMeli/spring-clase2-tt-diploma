package com.bootcamp.studentdiploma.service.dto;

public class DiplomaDTO {
    private String student;
    private double average;
    private String message;

    public String getStudent() {
        return student;
    }

    public DiplomaDTO setStudent(String student) {
        this.student = student;
        return this;
    }

    public double getAverage() {
        return average;
    }

    public DiplomaDTO setAverage(double average) {
        this.average = average;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public DiplomaDTO setMessage(String message) {
        this.message = message;
        return this;
    }
}
