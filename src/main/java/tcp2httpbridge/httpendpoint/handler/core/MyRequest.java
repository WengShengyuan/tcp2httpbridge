package tcp2httpbridge.httpendpoint.handler.core;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.sun.net.httpserver.HttpExchange;

public class MyRequest {
	
	public final static String GET = "GET";  
    public final static String POST = "POST";  
    
	private HttpExchange httpExchange;
	private Map<String, String> paramMap = new HashMap<String, String>();
	private Map<String, List<String>> headMap = new HashMap<String, List<String>>();
	private String requestBody = "";

	public MyRequest(HttpExchange httpExchange) {
		this.httpExchange = httpExchange;
	}

	public String getParamter(String param) {
		return paramMap.get(param);
	}

	public String getMethod() {
		return httpExchange.getRequestMethod().trim().toUpperCase();
	}

	public URI getReuestURI() {
		return httpExchange.getRequestURI();
	}

	public void initRequestParam() {
		String query = getReuestURI().getQuery();
		if (query != null) {
			String[] arrayStr = query.split("&");
			for (String str : arrayStr) {
				paramMap.put(str.split("=")[0], str.split("=")[1]);
			}
		}

	}

	public void initRequestHeader() {
		for (String s : httpExchange.getRequestHeaders().keySet()) {
			headMap.put(s, httpExchange.getRequestHeaders().get(s));
		}
	}

	public void initRequestBody() {
		InputStream in = httpExchange.getRequestBody(); // 获得输入流
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					in, "utf-8"));
			String temp = null;
			while ((temp = reader.readLine()) != null) {
				requestBody += temp;
			}

			if (requestBody != null) {
				requestBody = URLDecoder.decode(requestBody, "utf-8");
				String[] arrayStr = requestBody.split("&");
				for (String str : arrayStr) {
					paramMap.put(str.split("=")[0], str.split("=")[1]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String getRequestBody() {
		return requestBody;
	}

}
