package calculator;

import java.net.MalformedURLException;
import java.net.URL;

public class CalculatorClient {
	
	public static void main(String[] args) {
		try {
			CalculatorImplService service = new CalculatorImplService(new URL("http://localhost:8080/CalculatorSOAP_VM/calculator?wsdl"));
			Calculator c = service.getCalculatorImplPort();
			System.out.println(c.add(1.0, 2.0));
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
