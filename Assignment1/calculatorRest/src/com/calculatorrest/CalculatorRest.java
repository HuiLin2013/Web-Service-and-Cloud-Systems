package com.calculatorrest;

import java.util.Stack;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
 
@Path("/calc")
public class CalculatorRest {
 
    @GET
    @Path("/{equation}")
    @Produces(MediaType.TEXT_PLAIN)
    public String addPlainText(@PathParam("equation") String equation) {
    	String[] elements = equation.split("&");
    	Stack<Double> stack = new Stack<Double>();
    	double arg1, arg2, result, number;
    	for (int i = 0; i < elements.length; i++) {
    		switch (elements[i]) {
	    		case "+":
	    			arg2 = (double)stack.pop();
	    			arg1 = (double)stack.pop();
	    			result = arg1 + arg2;
	    			stack.push(result);
	    			break;
	    		case "-":
	    			arg2 = (double)stack.pop();
	    			arg1 = (double)stack.pop();
	    			result = arg1 - arg2;
	    			stack.push(result);
	    			break;
	    		case ":":
	    			arg2 = (double)stack.pop();
	    			arg1 = (double)stack.pop();
	    			result = arg1 / arg2;
	    			stack.push(result);
	    			break;
	    		case "*":
	    			arg2 = (double)stack.pop();
	    			arg1 = (double)stack.pop();
	    			result = arg1 * arg2;
	    			stack.push(result);
	    			break;
	    		default:
	    			number = Double.parseDouble(elements[i]);
	    			stack.push(number);
    		}
    	}
    	
        return Double.toString(stack.pop());
    }
 

}