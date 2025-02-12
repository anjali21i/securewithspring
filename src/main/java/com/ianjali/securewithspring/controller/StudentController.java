package com.ianjali.securewithspring.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ianjali.securewithspring.bean.Student;

//import jakarta.annotation.PostConstruct;

@RestController
public class StudentController {

	private List<Student> student = new ArrayList<Student>(List.of(new Student(1, "Anjali", 28, "A2"),
			new Student(2, "Geet", 24, "A2"), new Student(3, "Veer", 27, "A3")));

//	@PostConstruct
//	private List<Student> createStudent(List<Student> student) {
//		student.add(new Student(1, "Anjali", 28, "A2"));
//		student.add(new Student(2, "Geet", 24, "A2"));
//		student.add(new Student(3, "Veer", 27, "A3"));
//		
//		return student;
//	}

	@GetMapping("/fetchAllStudents")
	public List<Student> getStudents() {

		if (student.size() > 0) {
			return student;
		}

		return null;
	}

	@PostMapping("/addStudent")
	public Student addStudent(@RequestBody Student newStudent) {
		student.add(newStudent);
		return newStudent;
	}
	
	
//	@PostMapping("/updateStudent")
//	public Student updateStudent(@RequestBody Student newStudent, int studentId) {
//		if(studentId == studentId)
//		student.add(newStudent);
//		return newStudent;
//	}
	
	

}
