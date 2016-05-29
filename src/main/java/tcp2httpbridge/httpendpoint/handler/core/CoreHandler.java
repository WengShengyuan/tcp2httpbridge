package tcp2httpbridge.httpendpoint.handler.core;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

public class CoreHandler implements HttpHandler {
	
	private static final Logger logger = LoggerFactory.getLogger(CoreHandler.class); 
    public void handle(HttpExchange httpExchange) throws IOException {  
        MyRequest request = new MyRequest(httpExchange);  
        MyResponse response = new MyResponse(httpExchange);  
        MyHandler handler = ContextLoader.getHandler(request.getReuestURI().getPath());  
        handler.service(request, response);  
    }  

}
