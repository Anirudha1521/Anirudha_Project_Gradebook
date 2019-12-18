package com.restapi.student.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public interface StudentResource {
	
	@GET
	@Produces("application/xml")
	public Response getAllStudents();
	
	@GET
	@Path("{name}")
	@Produces("application/xml")
	public Response getStudent(@PathParam("name") String name);
	
	@POST
    @Path("{name}/grade/{grade : (.+)?}")
    @Consumes("application/xml")
	@Produces("application/xml")
	public Response createStudent(@PathParam("name") String name, @PathParam("grade") String grade);
	
	@PUT
    @Path("{name}/grade/{grade : (.+)?}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response updateStudent(@PathParam("name") String name, @PathParam("grade") String grade);
	
	@DELETE
	@Path("{name}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response deleteStudent(@PathParam("name") String name);
	

}
