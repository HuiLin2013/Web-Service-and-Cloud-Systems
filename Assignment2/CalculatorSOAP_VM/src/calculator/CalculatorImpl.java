package calculator;

import javax.jws.WebService;

@WebService(endpointInterface = "calculator.Calculator")
public class CalculatorImpl implements Calculator {

	@Override
	public Double add(double x, double y) {
		return x + y;
	}

	@Override
	public Double sub(double x, double y) {
		return x - y;
	}

	@Override
	public Double mul(double x, double y) {
		return x * y;
	}

	@Override
	public Double div(double x, double y) {
		return x / y;
	}

}
