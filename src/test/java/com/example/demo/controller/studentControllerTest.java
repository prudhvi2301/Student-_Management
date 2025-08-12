package com.example.demo.controller;

import com.example.demo.entity.student;
import com.example.demo.service.StudentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class) // Replace with your actual controller class name
class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testSaveData() throws Exception {
        student s = new student(1L, "John", "john@example.com", "Java", 5000.0, "Delhi");
        Mockito.when(studentService.saveData(any(student.class))).thenReturn(s);

        mockMvc.perform(post("/students") // Adjust endpoint
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(s)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.email").value("john@example.com"));
    }

    @Test
    void testGetAllData() throws Exception {
        List<student> students = Arrays.asList(
                new student(1L, "John", "john@example.com", "Java", 5000.0, "Delhi"),
                new student(2L, "Alice", "alice@example.com", "Python", 4500.0, "Mumbai")
        );
        Mockito.when(studentService.getAllData()).thenReturn(students);

        mockMvc.perform(get("/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    void testGetOneData() throws Exception {
        student s = new student(1L, "John", "john@example.com", "Java", 5000.0, "Delhi");
        Mockito.when(studentService.getOneData(1L)).thenReturn(s);

        mockMvc.perform(get("/students/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    void testUpdateData() throws Exception {
        student updated = new student(1L, "John Updated", "john.updated@example.com", "Java", 5500.0, "Chennai");
        Mockito.when(studentService.updateData(eq(1L), any(student.class))).thenReturn(updated);

        mockMvc.perform(put("/students/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updated)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.location").value("Chennai"));
    }

    @Test
    void testDeleteData() throws Exception {
        Mockito.doNothing().when(studentService).deleteData(1L);

        mockMvc.perform(delete("/students/1"))
                .andExpect(status().isOk());
    }
}
