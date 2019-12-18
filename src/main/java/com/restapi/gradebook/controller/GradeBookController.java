package com.restapi.gradebook.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.restapi.student.dto.GradeBook;
import com.restapi.student.dto.ParentGradeBook;
import com.restapi.student.dto.Student;
import com.restapi.student.dto.StudentGrade;

@Path("/gradebook")
public class GradeBookController implements GradeBookResource {
	

	 public static Map<Integer, List<Student>> studentDB = new ConcurrentHashMap<Integer, List<Student>>();
	 public List<String> validGrades = Arrays.asList("A", "B", "C", "D", "E", "F", "I", "W", "Z");
	 public List<String> additionalValidGrades = Arrays.asList("A+", "A-", "B+", "B-", "C-", "C+", "D+", "D-");
	 public static Map<String, Integer> gradeBookDB = new ConcurrentHashMap<String, Integer>(); 	 
	 public static Map<Integer, List<Student>> secondaryGradeBookDB = new ConcurrentHashMap<Integer, List<Student>>(); 

	@Override
	public Response getAllGradeBooks() {
		
		List<GradeBook> gradeBookList = new ArrayList();
		  for (Map.Entry<String,Integer> entry : gradeBookDB.entrySet()) {
			  GradeBook gradeBook = new GradeBook();
				gradeBook.setName(entry.getKey());
				gradeBook.setId(entry.getValue());
				gradeBookList.add(gradeBook);
		  }
		  ParentGradeBook parentGradeBook = new ParentGradeBook();
		  parentGradeBook.setGradeBookList(gradeBookList);
		return Response.status(Status.OK).entity(parentGradeBook).build();
	}


	@Override
	public Response createGradeBook(String name) {
		Integer gradeBookId = gradeBookDB.get(name);
		if(gradeBookId == null) {
			//Create new gradeBook.
			Random random = new Random();
			gradeBookDB.put(name, random.nextInt(9999) + 1);
			studentDB.put(gradeBookDB.get(name), new ArrayList<Student>());
		}else{
			return Response.status(Status.BAD_REQUEST).entity("GradeBook Title Already exists").build();
		}
		GradeBook gradeBook = new GradeBook();
		gradeBook.setId(gradeBookDB.get(name));
		
		return Response.status(Status.OK).entity(gradeBook).build();
	}


	@Override
	public Response updateGradeBook(String name) {
		return createGradeBook(name);
	}

	
	 
	 
	
	public Response getAllGradeBookStudents(Integer id) {
		boolean isValidGradeBook = isGradeBookValid(id);
		 if(!isValidGradeBook){
			 return Response.status(Status.BAD_REQUEST).build();
		 }
		List<Student> studentList = studentDB.get(id);
		StudentGrade studentGrade = new StudentGrade();
		studentGrade.setStudentList(studentList);
		return Response.status(Status.OK).entity(studentGrade).build();
	}


	public Response getSingleGradeBookStudent(Integer id, String name) {
		boolean isValidGradeBook = isGradeBookValid(id);
		 if(!isValidGradeBook){
			 return Response.status(Status.BAD_REQUEST).build();
		 }
		List<Student> studentList = studentDB.get(id);
		Student studentRecord = studentList.stream().filter(student -> student.getName().equals(name)).findFirst().get();
		if(studentRecord == null) {
			return Response.status(Status.NOT_FOUND).build();
		}else {
		return Response.status(Status.OK).entity(studentRecord).build();
		}
	}

	public Response createStudent(Integer id, String name, String grade) {
		return createOrUpdate(id, name, grade);
	}


	public Response updateStudent(Integer id, String name, String grade) {
		return createOrUpdate(id, name, grade);
	}


	public Response deleteStudent(Integer id, String name) {
		
		boolean isValidGradeBook = isGradeBookValid(id);
		 if(!isValidGradeBook){
			 return Response.status(Status.BAD_REQUEST).entity("No GradeBook found for the passed in Id ="+id).build();
		 }
		List<Student> studentList = studentDB.get(id);
		boolean isRemoved = studentList.removeIf(student -> student.getName().equals(name));
		if(!isRemoved) {
			return Response.status(Status.NOT_FOUND).entity("Failed to remove student record from primary server").build();
		}else {
			//remove from secondary 
			List<Student> secondStudentList = secondaryGradeBookDB.get(id);
			if(secondStudentList != null) {
			boolean isRemovedFromSecondary = secondStudentList.removeIf(student -> student.getName().equals(name));
				if(!isRemovedFromSecondary) {
					//return Response.status(Status.NOT_FOUND).entity("Failed to remove student record from Secondary server").build();
				}
			}
			return Response.status(Status.OK).build();
		}
	}
	
	
	
	// Below are the Utility methods.
	
	
	public static boolean isGradeBookValid(Integer id){
		boolean isValidGradeBook = false;
		 for (Map.Entry<String,Integer> entry : gradeBookDB.entrySet()) {
				Integer gradeBookId = entry.getValue();
				if(gradeBookId.intValue() == id.intValue()) {
					isValidGradeBook = true;
					break;
				}
		  }
		 return isValidGradeBook;
	}
	
	public static boolean isSecondaryGradeBookValid(Integer id){
		boolean isValidGradeBook = true;
		List<Student> gradeBookCopy = secondaryGradeBookDB.get(id);
		if(gradeBookCopy != null){
			isValidGradeBook = false;
		}
		 return isValidGradeBook;
	}
	
	private Response createOrUpdate(Integer id, String name, String grade) {
		
		boolean isValidGradeBook = isGradeBookValid(id);
		
		 if(!isValidGradeBook){
			 return Response.status(Status.BAD_REQUEST).entity("No GradeBook found for the passed in Id ="+id).build();
		 }
		 
		 if (isValidGrade(grade)) {
			List<Student> studentList = studentDB.get(id);
			if (studentList == null) {
				return Response.status(Status.BAD_REQUEST).build();
			} else {
				Student studentRecord = studentList.stream().filter(student -> student.getName().equals(name)).findFirst().orElse(null);
				if(studentRecord == null){
					createStudentRecord(name, grade, id);
				}else{
					studentRecord.setGrade(grade);
					List<Student> secondaryStudentList = secondaryGradeBookDB.get(id);
					if(secondaryStudentList != null) {
					Student secondaryStudent = secondaryStudentList.stream().filter(student -> student.getName().equals(name)).findFirst().orElse(null);
					if(secondaryStudent != null){
						secondaryStudent.setGrade(grade);
					}
					}
				}
			}
			return Response.status(Status.OK).build();
		} else {
			return Response.status(Status.BAD_REQUEST).build();
		}
	}
	
	private void createStudentRecord(String name, String grade, Integer id){
		
		List<Student> studentList = studentDB.get(id);
		if(studentList != null){
			Student newStudent = new Student();
			newStudent.setName(name);
			newStudent.setGrade(grade);
			studentList.add(newStudent);
		}
		List<Student> secondaryStudentList = secondaryGradeBookDB.get(id);
		if(secondaryStudentList != null) {
			Student newStudent = new Student();
			newStudent.setName(name);
			newStudent.setGrade(grade);
			secondaryStudentList.add(newStudent);
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
