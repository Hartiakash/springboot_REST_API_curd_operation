package com.example.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.dto.student;

public interface StudentRepository extends JpaRepository<student, Long>{

	List<student> findByName(String name);
	List<student> findByResult(String result);



}
