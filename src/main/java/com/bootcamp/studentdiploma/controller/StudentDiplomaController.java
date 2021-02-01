package com.bootcamp.studentdiploma.controller;

import com.bootcamp.studentdiploma.controller.dto.StudentDTO;
import com.bootcamp.studentdiploma.service.StudenDiplomaServiceException;
import com.bootcamp.studentdiploma.service.StudentDiplomaService;
import com.bootcamp.studentdiploma.service.dto.DiplomaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/student")

public class StudentDiplomaController {
    @Autowired
    StudentDiplomaService service;

    @PostMapping
    @ResponseBody
    public DiplomaDTO generateDiploma (@RequestBody StudentDTO student) {
        try {
            return service.generateDiploma(student);
        } catch (final Exception e) {
            final HttpStatus status;
            if (e instanceof StudenDiplomaServiceException) {
                status = HttpStatus.BAD_REQUEST;
            } else {
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
            throw new ResponseStatusException(status, e.getMessage());
        }
    }
}
