package com.calculatorrest;

import java.util.ArrayList;
import java.util.EmptyStackException;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.spi.resource.Singleton;

@Path("/calc2")
@Singleton
/**
 * Statefull calculator.
 */
public class CalculatorRest2 {

	private ArrayList<String> equations;
	
	private Calculator calculator;
	
	public CalculatorRest2() {
		equations = new ArrayList<String>();
		calculator = new Calculator();
	}
	
	@GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEquation(@PathParam("id") int id) {
		// Check if the equation exists.
		String returnValue = null;
		try {
			System.out.println("Getting: " + id);
			System.out.println("Size: " + equations.size());
			returnValue = equations.get(id);
			if (returnValue == null) {
				return Response.status(404).build();
			}
		} catch (IndexOutOfBoundsException e) {
			return Response.status(404).build();
		}
		return Response.ok(returnValue).build();
	}
	
	@PUT
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response putEquation(@PathParam("id") int id, @FormParam("equation") String equation) {
		// Check if the equation exists.
		if (equations.size() <= id || id < 0 || equations.get(id) == null) {
			return Response.status(404).build();
		}
		
		// See if we have to replace a ACC occurrence.
		System.out.println("Before replace: " + equation);
		if (equation.contains("ACC")) {
			equation = equation.replaceAll("ACC", equations.get(id));
		}
		System.out.println("After replace: " + equation);
		
		// Calculate the equation.
		String value = null;
    	try {
    		value = calculator.calculate(equation);
    	} catch (EmptyStackException e) {
    		return Response.status(400).entity("syntax error").build();
    	} catch (ArithmeticException e) {
    		return Response.status(400).entity("division by zero").build();
    	} catch (NumberFormatException e) {
    		return Response.status(400).entity("syntax error").build();
    	}
    	if (value == null) {
    		return Response.status(400).entity("syntax error").build();
    	}
    	
    	// Store the equation.
    	equations.set(id, value);
    	return Response.ok().build();
	}
	
	@DELETE
	@Path("/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteEquation(@PathParam("id") int id) {
		// Check if the equation exists.
		if (equations.size() <= id || id < 0 || equations.get(id) == null) {
			return Response.status(404).build();
		}
		
		// Delete the equation.
		equations.set(id, null);
		return Response.status(204).build();
	}
	
	@GET
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getEquations() {
		// Collect and return all equation IDs.
		StringBuilder s = new StringBuilder();
		s.append("");
		for (int i = 0; i < equations.size(); i++) {
			String id = equations.get(i);
			if (id != null) {
				s.append(id).append(",");
			}
		}
		return Response.ok(s.toString()).build();
	}
	
	@POST
    @Path("/")
    @Produces(MediaType.TEXT_PLAIN)
    public Response postEquation(@FormParam("equation") String equation) {
		// Calculate the equation.
		String value = null;
    	try {
    		value = calculator.calculate(equation);
    	} catch (EmptyStackException e) {
    		return Response.status(400).entity("syntax error").build();
    	} catch (ArithmeticException e) {
    		return Response.status(400).entity("division by zero").build();
    	} catch (NumberFormatException e) {
    		return Response.status(400).entity("syntax error").build();
    	}
    	if (value == null) {
    		return Response.status(400).entity("syntax error").build();
    	}
    	
    	// Store the equation.
    	equations.add(value);
    	System.out.println("Created: " + Integer.toString(equations.size() - 1));
    	System.out.println("Size: " + equations.size());
    	return Response.status(201).entity(Integer.toString(equations.size() - 1)).build();
	}
	
	@DELETE
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	public Response deleteEquations() {
		// Delete all equations.
		for (int i = 0; i < equations.size(); i++) {
			equations.set(i, null);
		}
		return Response.status(204).build();
	}
}