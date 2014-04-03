package com.calculatorrest;

import java.util.EmptyStackException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
 
@Path("/calc")
/**
 * Simple stateless calculator.
 */
public class CalculatorRest {
 
    @GET
    @Path("/{equation}")
    @Produces(MediaType.TEXT_PLAIN)
    /**
     * Evaluates and returns a given RPN equation.
     * @param equation
     * @return
     */
    public Response calculate(@PathParam("equation") String equation) {
    	Calculator calculator = new Calculator();
    	String returnValue = null;
    	try {
    		returnValue = calculator.calculate(equation);
    	} catch (EmptyStackException e) {
    		return Response.status(400).entity("syntax error").build();
    	} catch (ArithmeticException e) {
    		return Response.status(400).entity("division by zero").build();
    	} catch (NumberFormatException e) {
    		return Response.status(400).entity("syntax error").build();
    	}
    	if (returnValue == null) {
    		return Response.status(400).entity("syntax error").build();
    	}
    	return Response.ok(returnValue).build();
    }

}