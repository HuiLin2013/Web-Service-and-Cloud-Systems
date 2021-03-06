
package calculator.core;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlSeeAlso;
import javax.xml.ws.Action;
import javax.xml.ws.RequestWrapper;
import javax.xml.ws.ResponseWrapper;


/**
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.4-b01
 * Generated source version: 2.2
 * 
 */
@WebService(name = "Calculator", targetNamespace = "http://core.calculator/")
@XmlSeeAlso({
    ObjectFactory.class
})
public interface Calculator {


    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "sub", targetNamespace = "http://core.calculator/", className = "calculator.core.Sub")
    @ResponseWrapper(localName = "subResponse", targetNamespace = "http://core.calculator/", className = "calculator.core.SubResponse")
    @Action(input = "http://core.calculator/Calculator/subRequest", output = "http://core.calculator/Calculator/subResponse")
    public double sub(
        @WebParam(name = "arg0", targetNamespace = "")
        double arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        double arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "mul", targetNamespace = "http://core.calculator/", className = "calculator.core.Mul")
    @ResponseWrapper(localName = "mulResponse", targetNamespace = "http://core.calculator/", className = "calculator.core.MulResponse")
    @Action(input = "http://core.calculator/Calculator/mulRequest", output = "http://core.calculator/Calculator/mulResponse")
    public double mul(
        @WebParam(name = "arg0", targetNamespace = "")
        double arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        double arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "div", targetNamespace = "http://core.calculator/", className = "calculator.core.Div")
    @ResponseWrapper(localName = "divResponse", targetNamespace = "http://core.calculator/", className = "calculator.core.DivResponse")
    @Action(input = "http://core.calculator/Calculator/divRequest", output = "http://core.calculator/Calculator/divResponse")
    public double div(
        @WebParam(name = "arg0", targetNamespace = "")
        double arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        double arg1);

    /**
     * 
     * @param arg1
     * @param arg0
     * @return
     *     returns double
     */
    @WebMethod
    @WebResult(targetNamespace = "")
    @RequestWrapper(localName = "add", targetNamespace = "http://core.calculator/", className = "calculator.core.Add")
    @ResponseWrapper(localName = "addResponse", targetNamespace = "http://core.calculator/", className = "calculator.core.AddResponse")
    @Action(input = "http://core.calculator/Calculator/addRequest", output = "http://core.calculator/Calculator/addResponse")
    public double add(
        @WebParam(name = "arg0", targetNamespace = "")
        double arg0,
        @WebParam(name = "arg1", targetNamespace = "")
        double arg1);

}
