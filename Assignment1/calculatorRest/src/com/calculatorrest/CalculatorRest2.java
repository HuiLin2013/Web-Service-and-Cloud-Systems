package com.calculatorrest;

import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/calc2")
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
			returnValue = equations.get(id);
		} catch (IndexOutOfBoundsException e) {
			return Response.status(404).build();
		}
		return Response.ok(returnValue).build();
	}
	
	@PUT
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response calculate(@PathParam("id") int id) {
		// Check if the equation exists.
		String returnValue = null;
		try {
			returnValue = equations.get(id);
		} catch (IndexOutOfBoundsException e) {
			return Response.status(404).build();
		}
		return Response.ok(returnValue).build();
	}
}
