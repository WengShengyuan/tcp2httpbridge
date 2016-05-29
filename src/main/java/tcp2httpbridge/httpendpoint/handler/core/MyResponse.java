package tcp2httpbridge.httpendpoint.handler.core;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

import com.sun.net.httpserver.HttpExchange;

public class MyResponse {
	private HttpExchange httpExchange;
	public MyResponse(HttpExchange httpExchange){
		this.httpExchange = httpExchange;
	}
	
	
	public void write(String result) {
		try {
			httpExchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, result.getBytes().length);//设置响应头属性及响应信息的长度  
			OutputStream out = httpExchange.getResponseBody(); //获得输出流  
			out.write(result.getBytes());
			out.flush();
			httpExchange.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		
	}
}
