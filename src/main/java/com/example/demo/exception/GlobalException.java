package com.example.demo.exception;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalException {

	@ExceptionHandler(StudentException.class)
	public ResponseEntity<Map<String, Object>> handleStudentNotFound(StudentException ex) {
	      Map<String, Object> body = new HashMap<>();
	      body.put("timestamp", LocalDateTime.now());
	      body.put("error", ex.getMessage());
	      body.put("status", HttpStatus.NOT_FOUND.value());
	      return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
	    }

	  @ExceptionHandler(Exception.class)
	  public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
	      Map<String, Object> body = new HashMap<>();
	      body.put("timestamp", LocalDateTime.now());
	      body.put("error", ex.getMessage());
	      body.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
	      return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
	    }
	}



