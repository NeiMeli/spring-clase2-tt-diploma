package com.bootcamp.studentdiploma.service;

import com.bootcamp.studentdiploma.controller.dto.GradeDTO;
import com.bootcamp.studentdiploma.controller.dto.StudentDTO;
import com.bootcamp.studentdiploma.service.dto.DiplomaDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.bootcamp.studentdiploma.util.RoundUtil.roundOneDecimal;


@Service
public class StudentDiplomaService {
    public static final String CONGRATS_MESSAGE = "Gran promedio! Felicitado";
    public static final String STANDARD_MESSAGE = "Diploma entregado";
    private static final double CONGRATS_MINIMUM_GRADE = 9d;
    private static final double APPROVE_MINIMUM_GRADE = 6d;


    public DiplomaDTO generateDiploma(StudentDTO student) throws StudenDiplomaServiceException {
        validate(student);
        final double average = roundOneDecimal(student.getGrades()
                .stream()
                .mapToDouble(GradeDTO::getValue)
                .average()
                .orElse(0));
        if (didNotApprove(average)) {
            throw new StudenDiplomaServiceException(StudentDiplomaErrorImpl.NOT_APPROVED, average);
        }
        final String message = getMessage(average);
        return new DiplomaDTO()
                .setStudent(student.getName())
                .setAverage(average)
                .setMessage(message);
    }

    private boolean didNotApprove(double average) {
        return average < APPROVE_MINIMUM_GRADE;
    }

    private void validate(StudentDTO student) throws StudenDiplomaServiceException {
        List<GradeDTO> grades = student.getGrades();
        if (grades.isEmpty()) throw new StudenDiplomaServiceException(StudentDiplomaErrorImpl.NO_GRADES);
        Optional<Double> invalidGrade = grades.stream().map(GradeDTO::getValue).filter(g -> g <= 0d || g > 10d).findFirst();
        if (invalidGrade.isPresent()) {
            throw new StudenDiplomaServiceException(StudentDiplomaErrorImpl.INVALID_GRADE_VALUE, invalidGrade.get());
        }
    }

    private String getMessage(double average) {
        return average >= CONGRATS_MINIMUM_GRADE ? CONGRATS_MESSAGE : STANDARD_MESSAGE;
    }
}
