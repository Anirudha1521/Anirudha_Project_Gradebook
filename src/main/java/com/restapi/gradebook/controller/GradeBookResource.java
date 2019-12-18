package com.restapi.gradebook.controller;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

public interface GradeBookResource {
	
	@GET
	@Produces("application/xml")
	public Response getAllGradeBooks();
	
	@GET
	@Path("{id}/student")
	@Produces("application/xml")
	public Response getAllGradeBookStudents(@PathParam("id") Integer id);
	
	
	@GET
	@Path("{id}/student/{name}")
	@Produces("application/xml")
	public Response getSingleGradeBookStudent(@PathParam("id") Integer id, @PathParam("name") String name);
	
	@POST
    @Path("{name}")
    @Consumes("application/xml")
	@Produces("application/xml")
	public Response createGradeBook(@PathParam("name") String name);
	
	@PUT
    @Path("{name}")
    @Consumes("application/xml")
	@Produces("application/xml")
	public Response updateGradeBook(@PathParam("name") String name);
	
	@POST
    @Path("{id}/student/{name}/grade/{grade : (.+)?}")
    @Consumes("application/xml")
	@Produces("application/xml")
	public Response createStudent(@PathParam("id") Integer id, @PathParam("name") String name, @PathParam("grade") String grade);
	
	@PUT
    @Path("{id}/student/{name}/grade/{grade : (.+)?}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response updateStudent(@PathParam("id") Integer id, @PathParam("name") String name, @PathParam("grade") String grade);
	
	@DELETE
	@Path("{id}/student/{name}")
	@Consumes("application/xml")
	@Produces("application/xml")
	public Response deleteStudent(@PathParam("id") Integer id, @PathParam("name") String name);
	
	


}
