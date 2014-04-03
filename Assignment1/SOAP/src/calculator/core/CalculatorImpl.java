package calculator.core;

import javax.jws.WebService;

@WebService(endpointInterface = "calculator.core.Calculator")
/**
 * Actual implementation of the Calculator webservice.
 */
public class CalculatorImpl implements Calculator {

	@Override
	public double add(double x, double y) {
		return x + y;
	}

	@Override
	public double sub(double x, double y) {
		return x - y;
	}

	@Override
	public double mul(double x, double y) {
		return x * y;
	}

	@Override
	public double div(double x, double y) {
		return x / y;
	}

}
