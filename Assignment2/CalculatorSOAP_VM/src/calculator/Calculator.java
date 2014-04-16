package calculator;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.jws.soap.SOAPBinding.Style;

@WebService
@SOAPBinding(style = Style.RPC)
public interface Calculator {

	@WebMethod Double add(double x, double y);
	@WebMethod Double sub(double x, double y);
	@WebMethod Double mul(double x, double y);
	@WebMethod Double div(double x, double y);
}
