package tcp2httpbridge;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.httpendpoint.HTTPServer;
import tcp2httpbridge.tcpendpoint.TCPServer;

public class App {
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
		try {
			HTTPServer.start();
			TCPServer.startServer(1234, TCPServer.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void start(String type, int port){
		System.out.println("starting " + type + " , port : "+port+" ...");
	}

}
