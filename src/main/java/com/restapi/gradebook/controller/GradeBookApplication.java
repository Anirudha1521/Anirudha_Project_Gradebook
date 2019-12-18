package com.restapi.gradebook.controller;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.core.Application;

public class GradeBookApplication extends Application {

	   private Set<Object> singletons = new HashSet<Object>();
	   private Set<Class<?>> empty = new HashSet<Class<?>>();

	   public GradeBookApplication() {
	      singletons.add(new GradeBookController());
	      singletons.add(new SecondaryGradeBookController());
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
