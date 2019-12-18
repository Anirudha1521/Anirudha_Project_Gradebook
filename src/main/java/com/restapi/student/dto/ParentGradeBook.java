package com.restapi.student.dto;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "grade-book-list")
public class ParentGradeBook {
	
	private List<GradeBook> gradeBookList = new ArrayList<>();
	
	@XmlElement(name = "grade-book")
	public List<GradeBook> getGradeBookList() {
		return gradeBookList;
	}

	public void setGradeBookList(List<GradeBook> gradeBookList) {
		this.gradeBookList = gradeBookList;
	}

	public ParentGradeBook() {
		
	}

}
