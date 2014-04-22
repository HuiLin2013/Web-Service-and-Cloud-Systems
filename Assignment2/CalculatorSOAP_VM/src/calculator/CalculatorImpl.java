package calculator;

import javax.jws.WebService;

@WebService(endpointInterface = "calculator.Calculator")
public class CalculatorImpl implements Calculator {

	@Override
	public double sub(double arg0, double arg1) {
		return arg0 - arg1;
	}

	@Override
	public double mul(double arg0, double arg1) {
		return arg0 * arg1;
	}

	@Override
	public double div(double arg0, double arg1) {
		return arg0 / arg1;
	}

	@Override
	public double add(double arg0, double arg1) {
		return arg0 + arg1;
	}



}
