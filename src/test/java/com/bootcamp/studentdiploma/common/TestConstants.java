package com.bootcamp.studentdiploma.common;

import com.bootcamp.studentdiploma.controller.dto.GradeDTO;
import com.bootcamp.studentdiploma.controller.dto.StudentDTO;
import net.minidev.json.JSONValue;

import java.util.List;
import java.util.function.Function;

public class TestConstants {
    public static final StudentDTO APPROVED_STUDENT_STANDARD = new StudentDTO("s1", List.of (new GradeDTO("Lengua", 8d), new GradeDTO("Matematica", 6d)));
    public static final StudentDTO APPROVED_STUDENT_CONGRAT = new StudentDTO("s1", List.of (new GradeDTO("Lengua", 10d), new GradeDTO("Matematica", 9d)));
    public static final StudentDTO NOT_APPROVED_STUDENT = new StudentDTO("s1", List.of (new GradeDTO("Lengua", 4d), new GradeDTO("Matematica", 3.5d)));
    public static final Function<Object, String> toJson = JSONValue::toJSONString;
}
