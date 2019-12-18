package com.restapi.student.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Path;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.restapi.student.dto.Student;
import com.restapi.student.dto.StudentGrade;

@Path("/student")
public class StudentController implements StudentResource {
	
	 private Map<String, Student> studentDB =
             new ConcurrentHashMap<String, Student>();
	 private List<String> validGrades = Arrays.asList("A", "B", "C", "D", "E", "F", "I", "W", "Z");
	 private List<String> additionalValidGrades = Arrays.asList("A+", "A-", "B+", "B-", "C-", "C+", "D+", "D-");
	 
	 
	
	public Response getAllStudents() {
		Collection<Student> values = studentDB.values();
		StudentGrade studentGrade = new StudentGrade();
		
		List<Student> studentList = new ArrayList<Student>(values);
		studentGrade.setStudentList(studentList);
		GenericEntity<List<Student>> studententity = new GenericEntity<List<Student>>(studentList){};
		return Response.status(Status.OK).entity(studentGrade).build();
	}


	public Response getStudent(String name) {
		Student student = studentDB.get(name);
		if(student == null) {
			return Response.status(Status.NOT_FOUND).entity(student).build();
		}else {
		return Response.status(Status.OK).entity(student).build();
		}
	}


	public Response createStudent(String name, String grade) {
		return createOrUpdate(name, grade);
	}


	public Response updateStudent(String name, String grade) {
		return createOrUpdate(name, grade);
	}


	public Response deleteStudent(String name) {
		Student student = studentDB.remove(name);
		if(student == null) {
			return Response.status(Status.NOT_FOUND).build();
		}else {
			return Response.status(Status.OK).build();
		}
	}
	
	// Below are the Utility methods.
	
	private Response createOrUpdate(String name, String grade) {
		if (isValidGrade(grade)) {
			Student student = studentDB.get(name);
			if (student == null) {
				// create new student record
				Student newStudent = new Student();
				newStudent.setName(name);
				newStudent.setGrade(grade);
				studentDB.put(name, newStudent);
			} else {
				student.setGrade(grade);
				studentDB.put(name, student);
			}
			return Response.status(Status.OK).build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	private boolean isValidGrade(String grade) {
		boolean isValid = false;
		if (validGrades.contains(grade.toUpperCase()) || additionalValidGrades.contains(grade.toUpperCase())) {
			isValid = true;
		}
		return isValid;
	}

}
