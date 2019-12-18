package com.restapi.gradebook.controller;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.restapi.student.dto.Student;
import com.restapi.student.dto.StudentGrade;


@Path("/secondary")
public class SecondaryGradeBookController {
	

	@POST
    @Path("{id}")
    @Consumes("application/xml")
	@Produces("application/xml")
	public Response createSecondaryGradeBook(@PathParam("id") Integer id) {
		return createSecondaryCopy(id);
		
	}
	
	@PUT
    @Path("secondary/{id}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response updateSecondaryGradeBook(@PathParam("id") Integer id) {
		return createSecondaryCopy(id);
		
	}
	
	@GET
	@Path("{id}/student")
	@Produces("application/xml")
	public Response getAllGradeBookStudents(@PathParam("id") Integer id){
		if(!GradeBookController.isSecondaryGradeBookValid(id)){
			return Response.status(Status.BAD_REQUEST).entity("secondary gradebook already exists").build();
		}
		List<Student> studentList = GradeBookController.secondaryGradeBookDB.get(id);
		StudentGrade studentGrade = new StudentGrade();
		studentGrade.setStudentList(studentList);
		return Response.status(Status.OK).entity(studentGrade).build();
	}
	
	@GET
	@Path("{id}/student/{name}")
	@Produces("application/xml")
	public Response getAllGradeBookStudents(@PathParam("id") Integer id, @PathParam("name") String name){
		if(!GradeBookController.isSecondaryGradeBookValid(id)){
			return Response.status(Status.BAD_REQUEST).entity("secondary gradebook already exists").build();
		}
		List<Student> studentList = GradeBookController.secondaryGradeBookDB.get(id);
		Student studentRecord = studentList.stream().filter(student -> student.getName().equals(name)).findFirst().get();
		if(studentRecord == null) {
			return Response.status(Status.NOT_FOUND).build();
		}else {
		return Response.status(Status.OK).entity(studentRecord).build();
		}
	}
		
	@DELETE
	@Path("secondary/{id}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response deleteSecondaryGradeBook(@PathParam("id") Integer id){
		boolean isValidGradeBook = GradeBookController.isGradeBookValid(id);
		if(!isValidGradeBook){
			 return Response.status(Status.BAD_REQUEST).entity("There is no gradebook available").build();
		 }
		
		 List<Student> studentList = GradeBookController.secondaryGradeBookDB.remove(id);
		 if(studentList == null){
			 return Response.status(Status.BAD_REQUEST).entity("Failed to Delete").build();
		 }
		return Response.status(Status.OK).build();
		
	}
		
		
		private Response createSecondaryCopy(Integer id) {
			
			boolean isValidGradeBook = GradeBookController.isGradeBookValid(id);
			if(!isValidGradeBook){
				 return Response.status(Status.BAD_REQUEST).entity("There is no gradebook available").build();
			 }
			 
			if(!GradeBookController.isSecondaryGradeBookValid(id)){
				return Response.status(Status.BAD_REQUEST).entity("secondary gradebook already exists").build();
			}
			
			 List<Student> studentList = GradeBookController.studentDB.get(id);
			 
			 // create a secondary copy.
			 GradeBookController.secondaryGradeBookDB.put(id, studentList);
			 return Response.status(Status.OK).build();
		}
}
