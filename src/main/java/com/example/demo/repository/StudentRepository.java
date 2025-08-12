package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entity.student;

@Repository
public interface StudentRepository extends JpaRepository<student,Long> {
	
	
 List<student>findByLocation(String location);
}
