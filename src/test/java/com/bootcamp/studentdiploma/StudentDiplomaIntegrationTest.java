package com.bootcamp.studentdiploma;

import com.bootcamp.studentdiploma.common.TestConstants;
import com.bootcamp.studentdiploma.controller.dto.GradeDTO;
import com.bootcamp.studentdiploma.controller.dto.StudentDTO;
import com.bootcamp.studentdiploma.service.StudentDiplomaErrorImpl;
import com.bootcamp.studentdiploma.service.StudentDiplomaService;
import com.bootcamp.studentdiploma.service.dto.DiplomaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;

import static com.bootcamp.studentdiploma.common.TestConstants.toJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentDiplomaIntegrationTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    public static final String PATH = "/student";

    @Test
    void testHappy() throws Exception {
        String standardStudent = toJson.apply(TestConstants.APPROVED_STUDENT_STANDARD);
        MvcResult mvcResult1 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(standardStudent))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        DiplomaDTO diploma1 = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), DiplomaDTO.class);
        assertThat(diploma1.getMessage()).isEqualTo(StudentDiplomaService.STANDARD_MESSAGE);

        String congratStudent = toJson.apply(TestConstants.APPROVED_STUDENT_CONGRAT);
        MvcResult mvcResult2 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(congratStudent))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        DiplomaDTO diploma2 = objectMapper.readValue(mvcResult2.getResponse().getContentAsString(), DiplomaDTO.class);
        assertThat(diploma2.getMessage()).isEqualTo(StudentDiplomaService.CONGRATS_MESSAGE);

        String notApprovedStudent = toJson.apply(TestConstants.NOT_APPROVED_STUDENT);
        MvcResult mvcResult3 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(notApprovedStudent))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult3.getResolvedException())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(StudentDiplomaErrorImpl.NOT_APPROVED.getMessage(3.8d));
    }

    @Test
    void testBadRequests() throws Exception {
        StudentDTO studentWithoutGrades = new StudentDTO().setName("a").setGrades(Collections.emptyList());
        MvcResult mvcResult1 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(toJson.apply(studentWithoutGrades)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult1.getResolvedException())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(StudentDiplomaErrorImpl.NO_GRADES.getMessage());

        StudentDTO negativeGrade = new StudentDTO().setName("a").setGrades(List.of(new GradeDTO("b", -5)));
        MvcResult mvcResult2 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(toJson.apply(negativeGrade)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult2.getResolvedException())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(StudentDiplomaErrorImpl.INVALID_GRADE_VALUE.getMessage(-5));

        StudentDTO surpassedGrade = new StudentDTO().setName("a").setGrades(List.of(new GradeDTO("b", 15)));
        MvcResult mvcResult3 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(toJson.apply(surpassedGrade)))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();
        assertThat(mvcResult3.getResolvedException())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(StudentDiplomaErrorImpl.INVALID_GRADE_VALUE.getMessage(15));
    }
}
