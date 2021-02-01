package com.bootcamp.studentdiploma.controller;

import com.bootcamp.studentdiploma.common.TestConstants;
import com.bootcamp.studentdiploma.service.StudenDiplomaServiceException;
import com.bootcamp.studentdiploma.service.StudentDiplomaService;
import com.bootcamp.studentdiploma.service.StudentDiplomaServiceErrorForTest;
import com.bootcamp.studentdiploma.service.dto.DiplomaDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.web.server.ResponseStatusException;

import static com.bootcamp.studentdiploma.StudentDiplomaIntegrationTest.PATH;
import static com.bootcamp.studentdiploma.common.TestConstants.toJson;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class StudentDiplomaControllerTest {
    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentDiplomaService service;

    @Autowired
    MockMvc mockMvc;

    @Test
    void generateDiplomaHappy() throws Exception {
        final DiplomaDTO mockedResult = new DiplomaDTO().setStudent("a").setAverage(8d).setMessage("message");
        when(service.generateDiploma(any()))
                .thenReturn(mockedResult);
        String standardStudent = toJson.apply(TestConstants.APPROVED_STUDENT_STANDARD);
        MvcResult mvcResult1 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(standardStudent))
                .andDo(print())
                .andExpect(status().is(HttpStatus.OK.value()))
                .andReturn();
        DiplomaDTO actualResult = objectMapper.readValue(mvcResult1.getResponse().getContentAsString(), DiplomaDTO.class);
        assertThat(actualResult.getStudent()).isEqualTo(mockedResult.getStudent());
        assertThat(actualResult.getAverage()).isEqualTo(mockedResult.getAverage());
        assertThat(actualResult.getMessage()).isEqualTo(mockedResult.getMessage());
    }

    @Test
    void testBadRequest() throws Exception {
        when(service.generateDiploma(any()))
                .thenThrow(new StudenDiplomaServiceException(StudentDiplomaServiceErrorForTest.ERROR));
        String standardStudent = toJson.apply(TestConstants.APPROVED_STUDENT_STANDARD);
        MvcResult mvcResult1 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(standardStudent))
                .andDo(print())
                .andExpect(status().is(HttpStatus.BAD_REQUEST.value()))
                .andReturn();

        assertThat(mvcResult1.getResolvedException())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(StudentDiplomaServiceErrorForTest.ERROR.getMessage());
    }

    @Test
    void testInternalServerError() throws Exception {
        when(service.generateDiploma(any()))
                .thenThrow(new RuntimeException(StudentDiplomaServiceErrorForTest.ERROR.getMessage()));
        String standardStudent = toJson.apply(TestConstants.APPROVED_STUDENT_STANDARD);
        MvcResult mvcResult1 = mockMvc.perform(post(PATH)
                .contentType(MediaType.APPLICATION_JSON).content(standardStudent))
                .andDo(print())
                .andExpect(status().is(HttpStatus.INTERNAL_SERVER_ERROR.value()))
                .andReturn();

        assertThat(mvcResult1.getResolvedException())
                .isInstanceOf(ResponseStatusException.class)
                .hasMessageContaining(StudentDiplomaServiceErrorForTest.ERROR.getMessage());
    }
}