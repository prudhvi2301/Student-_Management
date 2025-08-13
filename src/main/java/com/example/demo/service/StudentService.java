package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entity.student;
import com.example.demo.exception.StudentException;
import com.example.demo.repository.StudentRepository;

@Service
public class StudentService {
	
	@Autowired
	private StudentRepository studentRepository;
	
	public student saveData(student students) {
		return studentRepository.save(students);
	}
	
	public List<student> getAllData(){
		return studentRepository.findAll();
	}
	public student getOneData(long id) {
		return studentRepository.findById(id).orElseThrow(()->new StudentException("Id not found"+id));
	}
	
	public student updateData(long id, student students) {
		student exit=studentRepository.findById(id).orElseThrow(()->new StudentException ("id"+id));
		exit.setName(students.getName());
		exit.setEmail(students.getEmail());
		exit.setFee(students.getFee());
		exit.setLocation(students.getLocation());
		
		return studentRepository.save(exit);
		
		}
	
	public void deleteData(long id) {
		student exit=studentRepository.findById(id).
				orElseThrow(()->new StudentException(" Student id not found"+id));
		studentRepository.delete(exit);
	}
	public List<student >findLocation(String location) {
		return studentRepository.findByLocation(location);
				
	}
	
}
