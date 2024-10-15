package com.example.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.demo.dto.student;
import com.example.demo.repository.StudentRepository;

@Controller
public class StudentController {
	
	@Autowired
	StudentRepository repository;
	

	// save one record
	@PostMapping("/students")
	public ResponseEntity<student> addRecord(@RequestBody student student) {
		student.setPercentage((student.getMaths() + student.getScience() + student.getEnglish()) / 3.0);
		if (student.getPercentage() < 35 || student.getMaths() < 35 || student.getScience() < 35
				|| student.getEnglish() < 35)
			student.setResult("Fail");
		else if (student.getPercentage() < 60)
			student.setResult("SecondClass");
		else if (student.getPercentage() < 85)
			student.setResult("Firstclass");
		else
			student.setResult("Distinction");

		return new ResponseEntity<student>(repository.save(student), HttpStatus.CREATED);
	}

	// save multiple record
	@PostMapping("/students/many")
	public ResponseEntity<List<student>> addRecords(@RequestBody List<student> students) {
		for (student student : students) {
			student.setPercentage((student.getMaths() + student.getScience() + student.getEnglish()) / 3.0);
			if (student.getPercentage() < 35 || student.getMaths() < 35 || student.getScience() < 35
					|| student.getEnglish() < 35)
				student.setResult("Fail");
			else if (student.getPercentage() < 60)
				student.setResult("SecondClass");
			else if (student.getPercentage() < 85)
				student.setResult("Firstclass");
			else
				student.setResult("Distinction");
		}

		return new ResponseEntity<List<student>>(repository.saveAll(students), HttpStatus.CREATED);
	}

	// fetch all records
	@GetMapping("/students")
	public ResponseEntity<List<student>> fetchAll() {
		List<student> students = repository.findAll();
		if (students.isEmpty())
			return new ResponseEntity<List<student>>(students, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<student>>(students, HttpStatus.FOUND);
	}
	
	//fetch by id
	@GetMapping("/students/{id}")
	public ResponseEntity<student> fetchById(@PathVariable long id){
		student student = repository.findById(id).orElse(null);
		if(student==null)
			return new ResponseEntity<student>(student, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<student>(student, HttpStatus.FOUND);
	}
	
	//fetch by name
	@GetMapping("/students/name/{name}")
	public ResponseEntity<List<student>> fetchByName(@PathVariable String name){
		List<student> students =repository.findByName(name);
		if (students.isEmpty())
			return new ResponseEntity<List<student>>(students, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<student>>(students, HttpStatus.FOUND);
	}
	
	
	@GetMapping("/students/result/distinction/{distinction}")
	public ResponseEntity<List<student>> fetchByDistinction(@PathVariable String result) {
	    List<student> students = repository.findByResult("Distinction");
	    if (students.isEmpty())
			return new ResponseEntity<List<student>>(students, HttpStatus.NOT_FOUND);
		else
			return new ResponseEntity<List<student>>(students, HttpStatus.FOUND);
	}
	
	
}
