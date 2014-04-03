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
 * Stateful calculator.
 */
public class CalculatorRest2 {

	/**
	 * Holds all equations. 
	 */
	private ArrayList<String> equations;
	
	/**
	 * Evaluates equations.
	 */
	private Calculator calculator;
	
	/**
	 * Initializes the list and the calculator.
	 */
	public CalculatorRest2() {
		equations = new ArrayList<String>();
		calculator = new Calculator();
	}
	
	@GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
	/**
	 * Retrieves the result of a given equation.
	 * @param id
	 * @return
	 */
    public Response getEquation(@PathParam("id") int id) {
		// Check if the equation exists.
		String returnValue = null;
		try {
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
	/**
	 * Put a new equation.
	 * @param id
	 * @param equation
	 * @return
	 */
    public Response putEquation(@PathParam("id") int id, @FormParam("equation") String equation) {
		// Check if the equation exists.
		if (equations.size() <= id || id < 0 || equations.get(id) == null) {
			return Response.status(404).build();
		}
		
		// See if we have to replace a ACC occurrence.
		if (equation.contains("ACC")) {
			equation = equation.replaceAll("ACC", equations.get(id));
		}
		
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
	/**
	 * Delete a given equation.
	 * @param id
	 * @return
	 */
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
	/**
	 * Retrieve all equations.
	 * @return
	 */
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
	/**
	 * Add a new equation.
	 * @param equation
	 * @return
	 */
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
    	return Response.status(201).entity(Integer.toString(equations.size() - 1)).build();
	}
	
	@DELETE
	@Path("/")
	@Produces(MediaType.TEXT_PLAIN)
	/**
	 * Delete all equations.
	 * @return
	 */
	public Response deleteEquations() {
		// Delete all equations.
		for (int i = 0; i < equations.size(); i++) {
			equations.set(i, null);
		}
		return Response.status(204).build();
	}
}