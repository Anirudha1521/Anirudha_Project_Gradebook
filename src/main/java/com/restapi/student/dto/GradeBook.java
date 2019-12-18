package com.restapi.student.dto;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class GradeBook {
	
	private Integer id;
	private String name;
	
	public GradeBook() {
		
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}
