package com.bootcamp.studentdiploma.service;

import com.bootcamp.studentdiploma.common.TestConstants;
import com.bootcamp.studentdiploma.controller.dto.GradeDTO;
import com.bootcamp.studentdiploma.controller.dto.StudentDTO;
import com.bootcamp.studentdiploma.service.dto.DiplomaDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@SpringBootTest
class StudentDiplomaServiceTest {
    @Autowired
    StudentDiplomaService service;
    @Test
    void getDiplomaHappy() throws StudenDiplomaServiceException {
        DiplomaDTO approvedStandard = service.generateDiploma(TestConstants.APPROVED_STUDENT_STANDARD);
        assertThat(approvedStandard.getMessage()).isEqualTo(StudentDiplomaService.STANDARD_MESSAGE);

        DiplomaDTO approvedCongrats = service.generateDiploma(TestConstants.APPROVED_STUDENT_CONGRAT);
        assertThat(approvedCongrats.getMessage()).isEqualTo(StudentDiplomaService.CONGRATS_MESSAGE);

        assertThatExceptionOfType(StudenDiplomaServiceException.class)
                .isThrownBy(() -> service.generateDiploma(TestConstants.NOT_APPROVED_STUDENT))
                .withMessageContaining(StudentDiplomaErrorImpl.NOT_APPROVED.getMessage(3.8d));
    }

    @Test
    void validateFails() {
        StudentDTO studentWithoutGrades = new StudentDTO().setName("a").setGrades(Collections.emptyList());
        assertThatExceptionOfType(StudenDiplomaServiceException.class)
                .isThrownBy(() -> service.generateDiploma(studentWithoutGrades))
                .withMessageContaining(StudentDiplomaErrorImpl.NO_GRADES.getMessage());

        StudentDTO negativeGrade = new StudentDTO().setName("a").setGrades(List.of(new GradeDTO("b", -5)));
        assertThatExceptionOfType(StudenDiplomaServiceException.class)
                .isThrownBy(() -> service.generateDiploma(negativeGrade))
                .withMessageContaining(StudentDiplomaErrorImpl.INVALID_GRADE_VALUE.getMessage(-5));

        StudentDTO surpassedGrade = new StudentDTO().setName("a").setGrades(List.of(new GradeDTO("b", 15)));
        assertThatExceptionOfType(StudenDiplomaServiceException.class)
                .isThrownBy(() -> service.generateDiploma(surpassedGrade))
                .withMessageContaining(StudentDiplomaErrorImpl.INVALID_GRADE_VALUE.getMessage(15));

    }
}