package com.bootcamp.studentdiploma.controller.dto;

public class GradeDTO {
    private String name;
    private double value;

    public GradeDTO(String name, double value) {
        this.name = name;
        this.value = value;
    }

    public GradeDTO() {
    }

    public String getName() {
        return name;
    }

    public GradeDTO setName(String name) {
        this.name = name;
        return this;
    }

    public double getValue() {
        return value;
    }

    public GradeDTO setValue(double value) {
        this.value = value;
        return this;
    }
}
