package com.calculatorrest;

import java.util.EmptyStackException;
import java.util.Stack;

public class Calculator {

	public String calculate(String equation) throws EmptyStackException, ArithmeticException, NumberFormatException {
		String[] elements = equation.split("&");
    	Stack<Double> stack = new Stack<Double>();
    	double arg1, arg2, result, number;
    	for (int i = 0; i < elements.length; i++) {
    		if (elements[i].equals("+")) {
				arg2 = (double)stack.pop();
    			arg1 = (double)stack.pop();
    			result = arg1 + arg2;
    			stack.push(result);	
    		} else if (elements[i].equals("-")) {
				arg2 = (double)stack.pop();
    			arg1 = (double)stack.pop();
    			result = arg1 - arg2;
        			stack.push(result);	
    		} else if (elements[i].equals(":")) {
				arg2 = (double)stack.pop();
    			arg1 = (double)stack.pop();    				
    			
    			// Check for division by zero.
    			if (arg2 == 0.0) {
    				throw new ArithmeticException();
    			} else {
    				result = arg1 / arg2;
        			stack.push(result);	
    			}
    		} else if (elements[i].equals("*")) {
				arg2 = (double)stack.pop();
    			arg1 = (double)stack.pop();
    			result = arg1 * arg2;
    			stack.push(result);	
    		} else {
				number = Double.parseDouble(elements[i]);
				stack.push(number);
    		}
    	}
    	
    	// There should only be one value left in the stack.
    	String returnValue = Double.toString(stack.pop()); 
    	if (stack.isEmpty()) {
    		return returnValue;
    	}
        return null;
	}
}
