package tcp2httpbridge;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tcp2httpbridge.common.ConfigLoader;
import tcp2httpbridge.common.StaticValue;
import tcp2httpbridge.httpendpoint.HTTPServer;
import tcp2httpbridge.tcpendpoint.TCPServer;

public class App {
	
	private static final Logger logger = LoggerFactory.getLogger(App.class);
	public static void main(String[] args) {
		
		if(args.length<1){
			System.out.println("USAGE : java -jar tcp2httpbridge.jar [HTTP/TCP]");
		} else if(args[0].equals(StaticValue.APPTYPE.HTTP)){
			HTTPServer.start();
		} else if(args[0].equals(StaticValue.APPTYPE.TCP)){
			TCPServer.startServer(Integer.parseInt(ConfigLoader.getInstance().getValue("local.tcp.port")), TCPServer.class);
		} else {
			System.out.println("USAGE : java -jar tcp2httpbridge.jar [HTTP/TCP]");
		}
	}
	
}
