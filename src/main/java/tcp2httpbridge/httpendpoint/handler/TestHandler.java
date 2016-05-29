package tcp2httpbridge.httpendpoint.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.httpendpoint.handler.core.MyHandler;
import tcp2httpbridge.httpendpoint.handler.core.MyRequest;
import tcp2httpbridge.httpendpoint.handler.core.MyResponse;

public class TestHandler extends MyHandler{
	
	private static final Logger logger = LoggerFactory.getLogger(TestHandler.class);  

	
	@Override
	public void doGet(MyRequest request, MyResponse response) {
		 doPost(request, response);
	}

	@Override
	public void doPost(MyRequest request, MyResponse response) {
		logger.info("接收请求:"+ request.getReuestURI().getPath());
		
		String string = request.getParamter("string");
		response.write("get: "+string);
	}


}
