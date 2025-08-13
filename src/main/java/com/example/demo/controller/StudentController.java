package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entity.student;
import com.example.demo.service.StudentService;

@RestController
@RequestMapping("/students")

public class StudentController {
	
	@Autowired
	private StudentService studentService;
	
	@PostMapping
	public ResponseEntity<student> createStudent(@RequestBody student students) {
		student studentData=studentService.saveData(students);
		return new ResponseEntity<>(studentData,HttpStatus.CREATED);
	}
	
	@GetMapping
	public ResponseEntity<List<student>>getAll(){
		List<student> studentData=studentService.getAllData();
		return new ResponseEntity<>(studentData,HttpStatus.OK);
	}
	
	@GetMapping("/id/{id}")
	public ResponseEntity<student> getId(@PathVariable long id) {
		student studentData=studentService.getOneData(id);
		return new ResponseEntity<>(studentData,HttpStatus.OK);
	}
	
	@PutMapping("/id/{id}")
	public ResponseEntity<student >updateStudent(@PathVariable long id , @RequestBody student students) {
		student studentData=studentService.updateData(id, students);
		return new ResponseEntity<>(studentData,HttpStatus.OK);
		
	}
	
	@DeleteMapping("/id/{id}")
	public ResponseEntity<Void>deleteStudent(@PathVariable long id) {
	 studentService.deleteData(id);
	return ResponseEntity.noContent().build();
	
	}
	@GetMapping("/location/{location}")
	public ResponseEntity<List<student>>locations(@PathVariable String location){
		List<student>studentLocation=studentService.findLocation(location);
		return new ResponseEntity<>(studentLocation,HttpStatus.OK);
	}

}
