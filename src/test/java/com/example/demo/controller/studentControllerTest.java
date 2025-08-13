package com.example.demo.controller;

import com.example.demo.entity.student;
import com.example.demo.exception.StudentException;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    private student sampleStudent;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        sampleStudent = new student();
        sampleStudent.setId(1L);
        sampleStudent.setName("John Doe");
        sampleStudent.setEmail("john@example.com");
        sampleStudent.setCourse("Java");
        sampleStudent.setFee(5000.0);
        sampleStudent.setLocation("New York");
    }

    @Test
    void testCreateStudent() throws Exception {
        Mockito.when(studentService.saveData(any(student.class))).thenReturn(sampleStudent);

        mockMvc.perform(post("/students")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sampleStudent)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.name", is("John Doe")))
            .andExpect(jsonPath("$.location", is("New York")));
    }

    @Test
    void testGetAllStudents() throws Exception {
        List<student> students = Arrays.asList(sampleStudent);
        Mockito.when(studentService.getAllData()).thenReturn(students);

        mockMvc.perform(get("/students"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(1)))
            .andExpect(jsonPath("$[0].name", is("John Doe")))
            .andExpect(jsonPath("$[0].location", is("New York")));
    }

    @Test
    void testGetStudentById_Found() throws Exception {
        Mockito.when(studentService.getOneData(1L)).thenReturn(sampleStudent);

        mockMvc.perform(get("/students/id/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("John Doe")))
            .andExpect(jsonPath("$.location", is("New York")));
    }

    @Test
    void testGetStudentById_NotFound() throws Exception {
        Mockito.when(studentService.getOneData(1L)).thenThrow(new StudentException("Student not found"));

        mockMvc.perform(get("/students/id/1"))
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.error", is("Student not found")))
            .andExpect(jsonPath("$.status", is(404)))
            .andExpect(jsonPath("$.timestamp").exists());
    }

    @Test
    void testUpdateStudent_Found() throws Exception {
        student updatedStudent = new student();
        updatedStudent.setId(1L);
        updatedStudent.setName("Jane Doe");
        updatedStudent.setEmail("jane@example.com");
        updatedStudent.setCourse("Spring Boot");
        updatedStudent.setFee(7000.0);
        updatedStudent.setLocation("Los Angeles");

        Mockito.when(studentService.updateData(eq(1L), any(student.class))).thenReturn(updatedStudent);

        mockMvc.perform(put("/students/id/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedStudent)))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.name", is("Jane Doe")))
            .andExpect(jsonPath("$.location", is("Los Angeles")));
    }

    @Test
    void testDeleteStudent_Found() throws Exception {
        Mockito.doNothing().when(studentService).deleteData(1L);

        mockMvc.perform(delete("/students/id/1"))
            .andExpect(status().isNoContent());
    }

    @Test
    void testFindByLocation() throws Exception {
        List<student> students = Arrays.asList(sampleStudent);
        Mockito.when(studentService.findLocation("New York")).thenReturn(students);

        mockMvc.perform(get("/students/location/New York"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.size()", is(1)))
            .andExpect(jsonPath("$[0].location", is("New York")));
    }
}
