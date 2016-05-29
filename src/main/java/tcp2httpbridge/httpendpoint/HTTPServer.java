package tcp2httpbridge.httpendpoint;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import tcp2httpbridge.httpendpoint.handler.core.ContextLoader;
import tcp2httpbridge.httpendpoint.handler.core.CoreHandler;

public class HTTPServer {
	
	private static final Logger logger = LoggerFactory.getLogger(HTTPServer.class);
	
	public static void start() throws IOException {
		ContextLoader.load();  
        int port = 8888;
        HttpServerProvider provider = HttpServerProvider.provider();  
        HttpServer httpserver =provider.createHttpServer(new InetSocketAddress(port), 100);//监听端口8080,能同时接 受100个请求  
        httpserver.createContext(ContextLoader.contextPath, new CoreHandler());   
        httpserver.setExecutor(null);  
        httpserver.start();  
        logger.info("端口开启成功 :" + port+"    同时接受请求数量：100");
	}
	

}
