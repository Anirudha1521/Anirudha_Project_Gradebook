package com.restapi.student.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "student-list")
public class StudentGrade {
	
	@XmlElement(name = "student")
	public List<Student> getStudentList() {
		return studentList;
	}

	public void setStudentList(List<Student> studentList) {
		this.studentList = studentList;
	}

	private List<Student> studentList = new ArrayList<>();
	
	public StudentGrade() {
		
	}

}
