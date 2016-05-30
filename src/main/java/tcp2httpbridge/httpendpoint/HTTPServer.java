package tcp2httpbridge.httpendpoint;

import java.io.IOException;
import java.net.InetSocketAddress;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.spi.HttpServerProvider;

import tcp2httpbridge.common.ConfigLoader;
import tcp2httpbridge.httpendpoint.handler.core.ContextLoader;
import tcp2httpbridge.httpendpoint.handler.core.CoreHandler;

public class HTTPServer {
	
	private static final Logger logger = LoggerFactory.getLogger(HTTPServer.class);
	
	public static void start() {
		try {
			ContextLoader.load();  
			HttpServerProvider provider = HttpServerProvider.provider();  
			HttpServer httpserver =provider.createHttpServer(
					new InetSocketAddress(Integer.parseInt(ConfigLoader.getInstance().getValue("local.http.port"))), 
					Integer.parseInt(ConfigLoader.getInstance().getValue("app.maxhttphandler")));
			
			httpserver.createContext(ContextLoader.contextPath, new CoreHandler());   
			httpserver.setExecutor(null);  
			httpserver.start();  
			logger.info("HTTP 服务开启成功 :" + Integer.parseInt(ConfigLoader.getInstance().getValue("local.http.port")));
		} catch (NumberFormatException e) {
			logger.error("HTTP 服务开启失败",e);
		} catch (IOException e) {
			logger.error("HTTP 服务开启失败",e);
		}
	}
	

}
