package calculator.core;

import javax.xml.ws.Endpoint;

/**
 * Publishes the WSDL file for the Calculator webservice.
 */
public class CalculatorPublisher {
	
	public static void main(String[] args) {
		Endpoint.publish("http://localhost:8080/WS/Calculator", new CalculatorImpl());
	}

}
