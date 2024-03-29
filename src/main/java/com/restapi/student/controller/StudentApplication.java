package com.restapi.student.controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class StudentApplication extends Application {

	   private Set<Object> singletons = new HashSet<Object>();
	   private Set<Class<?>> empty = new HashSet<Class<?>>();

	   public StudentApplication() {
	      singletons.add(new StudentController());
	   }

	   @Override
	   public Set<Class<?>> getClasses() {
	      return empty;
	   }

	   @Override
	   public Set<Object> getSingletons() {
	      return singletons;
	   }

}
