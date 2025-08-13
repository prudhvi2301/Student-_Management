package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.demo.entity.student;
import com.example.demo.exception.StudentException;
import com.example.demo.repository.StudentRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

class StudentServiceTest {

    @Mock
    private StudentRepository studentRepository;

    @InjectMocks
    private StudentService studentService;

    private student sampleStudent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        sampleStudent = new student();
        sampleStudent.setId(1L);
        sampleStudent.setName("John Doe");
        sampleStudent.setEmail("john@example.com");
        sampleStudent.setCourse("Java");
        sampleStudent.setFee(5000.0);
        sampleStudent.setLocation("New York");
    }

    @Test
    void testGetAllStudents() {
        when(studentRepository.findAll()).thenReturn(Arrays.asList(sampleStudent));

        List<student> students = studentService.getAllData();

        assertEquals(1, students.size());
        assertEquals("John Doe", students.get(0).getName());
        assertEquals("New York", students.get(0).getLocation());

        verify(studentRepository, times(1)).findAll();
    }

    @Test
    void testGetStudentById_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        student found = studentService.getOneData(1L);

        assertNotNull(found);
        assertEquals("John Doe", found.getName());
        assertEquals("New York", found.getLocation());

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testGetStudentById_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentException.class, () -> studentService.getOneData(1L));

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateStudent() {
        when(studentRepository.save(sampleStudent)).thenReturn(sampleStudent);

        student saved = studentService.saveData(sampleStudent);

        assertNotNull(saved);
        assertEquals("John Doe", saved.getName());
        assertEquals("New York", saved.getLocation());

        verify(studentRepository, times(1)).save(sampleStudent);
    }

    @Test
    void testUpdateStudent_Found() {
        student updatedDetails = new student();
        updatedDetails.setName("Jane Doe");
        updatedDetails.setEmail("jane@example.com");
        updatedDetails.setCourse("Spring Boot");
        updatedDetails.setFee(7000.0);
        updatedDetails.setLocation("Los Angeles");

        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));
        when(studentRepository.save(any(student.class))).thenReturn(updatedDetails);

        student updated = studentService.updateData(1L, updatedDetails);

        assertNotNull(updated);
        assertEquals("Jane Doe", updated.getName());
        assertEquals("Los Angeles", updated.getLocation());

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).save(any(student.class));
    }

    @Test
    void testUpdateStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentException.class, () -> studentService.updateData(1L, sampleStudent));

        verify(studentRepository, times(1)).findById(1L);
    }

    @Test
    void testDeleteStudent_Found() {
        when(studentRepository.findById(1L)).thenReturn(Optional.of(sampleStudent));

        studentService.deleteData(1L);

        verify(studentRepository, times(1)).findById(1L);
        verify(studentRepository, times(1)).delete(sampleStudent);
    }

    @Test
    void testDeleteStudent_NotFound() {
        when(studentRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(StudentException.class, () -> studentService.deleteData(1L));

        verify(studentRepository, times(1)).findById(1L);
    }
}
