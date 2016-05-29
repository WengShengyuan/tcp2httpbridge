package tcp2httpbridge.httpendpoint;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import tcp2httpbridge.common.StaticValue;
import tcp2httpbridge.httpendpoint.handler.core.ContextLoader;
import tcp2httpbridge.httpendpoint.handler.core.CoreHandler;

public class HTTPServer {
	
	private static final Logger logger = LoggerFactory.getLogger(HTTPServer.class);
	
	public static void start() throws IOException {
		ContextLoader.load();  
        HttpServerProvider provider = HttpServerProvider.provider();  
        HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(StaticValue.PARAM.HTTPPORT), StaticValue.PARAM.MAXHTTP);//监听端口8080,能同时接 受100个请求  
        httpserver.createContext(ContextLoader.contextPath, new CoreHandler());   
        httpserver.setExecutor(null);  
        httpserver.start();  
        logger.info("端口开启成功 :" + StaticValue.PARAM.HTTPPORT);
	}
	

}
