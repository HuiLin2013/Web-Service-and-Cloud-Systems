package calculator.core;

/**
 * Simple client that uses the Calculator webservice.
 */
public class CalculatorClient {
	
	public static void main(String[] args) {
		CalculatorImplService service = new CalculatorImplService();
		Calculator calculator = service.getCalculatorImplPort();
		System.out.println("====================");
		System.out.println("Running the Calculator");
		System.out.println("====================");
		System.out.println("2.0 + 3.0 = " + calculator.add(2.0, 3.0));
		System.out.println("2.0 - 3.0 = " + calculator.sub(2.0, 3.0));
		System.out.println("2.0 * 3.0 = " + calculator.mul(2.0, 3.0));
		System.out.println("2.0 / 3.0 = " + calculator.div(2.0, 3.0));
	}

}
