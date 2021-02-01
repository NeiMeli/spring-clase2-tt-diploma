package com.bootcamp.studentdiploma.controller.dto;

import java.util.List;

public class StudentDTO {
    private String name;
    private List<GradeDTO> grades;

    public StudentDTO(String name, List<GradeDTO> grades) {
        this.name = name;
        this.grades = grades;
    }

    public StudentDTO() {
    }

    public String getName() {
        return name;
    }

    public StudentDTO setName(String name) {
        this.name = name;
        return this;
    }

    public List<GradeDTO> getGrades() {
        return grades;
    }

    public StudentDTO setGrades(List<GradeDTO> grades) {
        this.grades = grades;
        return this;
    }
}
